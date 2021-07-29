package com.bat.velo.service;

import com.bat.velo.dto.*;
import com.bat.velo.entity.Files;
import com.bat.velo.entity.NewsAndEvent;
import com.bat.velo.entity.User;
import com.bat.velo.repository.FilesRepository;
import com.bat.velo.repository.NewsAndEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
public class NewsService {
    @Autowired
    NewsAndEventRepository newsAndEventRepository;
    @Autowired
    FilesRepository filesRepository;

    @Autowired
    private EntityManager entityManager;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AuditService auditService;

    public boolean addNewsOrEvent(NewsAndEventDTO request,String userId){
        NewsAndEvent newsAndEvent=new NewsAndEvent();
        BeanUtils.copyProperties(request,newsAndEvent);
        newsAndEventRepository.save(newsAndEvent);
        auditService.create(request.getType()==1?"news":"event", userId, newsAndEvent);
        return true;
    }
    public boolean updateNewsOrEvent(NewsAndEventDTO request,String userId) throws Exception {
        Optional<NewsAndEvent> existing=newsAndEventRepository.findById(request.getId());
        if(existing.isPresent()){
            NewsAndEvent newsAndEvent=new NewsAndEvent();
            BeanUtils.copyProperties(request,newsAndEvent);
            newsAndEventRepository.save(newsAndEvent);
            auditService.update(request.getType()==1?"news":"event", userId, existing.get(), newsAndEvent);
            return true;

        }else
            throw new Exception("News or Event Not Found");
    }


    public NewsAndEventDTO get(long newsId) {

        Optional<NewsAndEvent> newsAndEvent = newsAndEventRepository.findById(newsId);

        NewsAndEventDTO newsAndEventDTO = new NewsAndEventDTO();
        BeanUtils.copyProperties(newsAndEvent.get(), newsAndEventDTO);

        return newsAndEventDTO;
    }


    public Boolean changeActiveDeactive(NewsActivateDto request,String userId) throws Exception {

        Optional<NewsAndEvent> newsAndEvent = newsAndEventRepository.findById(request.getNewsId());
        if(newsAndEvent.isPresent()){
            NewsAndEvent newsAndEvents=newsAndEvent.get();
            BeanUtils.copyProperties(newsAndEvent.get(), newsAndEvents);

            newsAndEvents.setStatus(request.getStatus());
            newsAndEventRepository.save(newsAndEvents);
            auditService.update(newsAndEvent.get().getType()==1?"news":"event", userId, newsAndEvent.get(), newsAndEvents);
            return true;

        }
        else
            throw new Exception("User Not Found");

    }

    public boolean deleteData(long newsId,String userId) throws Exception{
        Optional<NewsAndEvent> newsAndEvent = newsAndEventRepository.findById(newsId);

        if(newsAndEvent.isPresent()){
            newsAndEventRepository.delete(newsAndEvent.get());
            auditService.delete(newsAndEvent.get().getType()==1?"news":"event",userId,newsAndEvent.get());
            return true;
        }else
            throw new Exception("News Or Event not found");
    }

    public List<NewsAndEventDTO> searchUser(FilterDto request, int pageNum, int pageSize) {

        List<NewsAndEventDTO> result = new ArrayList<>();
        System.out.println(request);
        Map<String, Object> params = new HashMap<>();
        String sqlFilter = "";
        String sql = "select id,SUBSTRING(content, 1, 100) as content,status,title,type,video_url,created_by,created_date,updated_by,updated_date from vlo_news_and_event where ";
        List<String>filters=new ArrayList<>();
        List<String>status=new ArrayList<>();
        List<String>types=new ArrayList<>();

        if(request.getFilter()!=null&&!request.getFilter().isEmpty()){
            List<String> listParams = Arrays.asList(request.getFilter().split(","));
            int no = 0;
            for (String param: listParams) {
                filters.add(" title LIKE :title"+no);
                params.put("title"+no,"%"+param+"%");
                no++;
                System.out.println("'%"+param+"%'");
            }

        }
        if(request.getStatus()!=null&&!request.getStatus().isEmpty()){
            if(request.getStatus().contains("de")){
                status.add(" status = :status ");
                params.put("status",0);
            }
            else if(request.getStatus().contains("all")){

            }else if(request.getStatus().equals("active")){
                status.add(" status = :status ");
                params.put("status",1);
            }
            System.out.println(params);
        }
        if(request.getType()!=null){
            if(request.getType()!=0){
                types.add(" type = :type");
                params.put("type",request.getType());
            }
        }


        if(filters.size()!=0){
            for(int i=0;i<filters.size();i++){

                sqlFilter+=filters.get(i);
                if(i!=filters.size()-1){
                    sqlFilter+=" or";
                }
            }
            if(types.size()!=0||status.size()!=0)
                sqlFilter+=" and ";

        }
        if(status.size()!=0){
            for(int i=0;i<status.size();i++){

                sqlFilter+=status.get(i);
                if(i!=status.size()-1){
                    sqlFilter+=" or";
                }
            }
            if(types.size()!=0)
                sqlFilter+=" and ";

        }

        if(types.size()!=0){
            sqlFilter+=types.get(0);
        }


        String sqls = sql+" "
                + sqlFilter
                + " order by title asc "
                + ((pageSize > 0) ? " limit " + ((pageNum - 1) * pageSize) + "," + pageSize : "");
        Query query = entityManager.createNativeQuery(sqls, NewsAndEvent.class);
        System.out.println(sqls);
        for (String key : params.keySet()) {

            query.setParameter(key,params.get(key));
        }

        List<NewsAndEvent> newsAndEvents = query.getResultList();

        for (NewsAndEvent newsAndEvent : newsAndEvents) {

            NewsAndEventDTO dto = new NewsAndEventDTO();

            BeanUtils.copyProperties(newsAndEvent, dto);

            result.add(dto);
        }

        return result;
    }

    public void addFile(long newsId, String fileName, String fileData) throws Exception {
        Optional<NewsAndEvent> newsAndEvent = newsAndEventRepository.findById(newsId);
        if (newsAndEvent.isPresent()) {

            Files file = filesRepository.findByForeignIdAndFileType(newsAndEvent.get().getId(), "news");

            if (file == null) {
                
                file = Files.builder()
                        .foreignId(file.getId())
                        .fileType("news")
                        .build();
            }

            file.setFileData(fileData);
            file.setFileName(fileName);

            filesRepository.save(file);
        }
        else
            throw new Exception("User not found");
    }
    
    public FilesDTO getFile(long newsId) throws Exception {

        FilesDTO ret = null;
        Optional<NewsAndEvent> newsAndEvent = newsAndEventRepository.findById(newsId);

        if (newsAndEvent.isPresent()) {

            Files file = filesRepository.findByForeignIdAndFileType(newsId, "news");

            if (file == null)
                throw new Exception("File not found");

            ret = new FilesDTO();
            BeanUtils.copyProperties(file, ret);
        }
        else
            throw new Exception("User not found");

        return ret;
    }
}
