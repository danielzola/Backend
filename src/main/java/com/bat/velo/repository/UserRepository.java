package com.bat.velo.repository;

import com.bat.velo.entity.User;
import io.micrometer.core.instrument.util.StringEscapeUtils;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
    @Query(value = "SELECT * FROM vlo_user t where t.email_address = :email_address",
            nativeQuery=true
    )
    User findByUserId(String email_address);
    
    @Query(value = "SELECT * FROM vlo_user t where t.Email_Address = :userName and Password=:password " ,
            nativeQuery=true
    )
    User findByUserIdAndPasss(String userName,String password);
    
    @Query(value = "SELECT * FROM vlo_user t where t.Referal_Code=:referalCode and Is_Verified=1 and role_code = 3 and Is_Active=1",nativeQuery = true)
    User findIsValidByReferal(String referalCode);
    
    @Query(value = "SELECT Id FROM vlo_user t where t.Referal_Code=:referalCode and Email_Address=:emailAddress",nativeQuery = true)
    Integer findIsValidByReferal(String referalCode,String emailAddress);
    
    @Query(value = "Select * from vlo_user t where t.Created_By LIKE %:createdBy%",nativeQuery = true)
    List<User> findByUserCreatedByLike(@Param("createdBy") String createdBy);
    
    @Query(value = "SELECT * FROM vlo_user t where role_code=:codeRole1 or role_code=:codeRole2",nativeQuery = true)
    List<User>findByDualRoleCode(long codeRole1,long codeRole2);
    
    List<User>findByRoleCode(long roleCode);
    
    @Query(value = "Select * from vlo_user t where t.Email_Address LIKE %:emailAddress%",nativeQuery = true)
    List<User> findByUserUserIdByLike(@Param("emailAddress") String emailAddress);

    User findByReferalCode(String referalCode);

    User findByParentReferalCode(String parentReferalCode);
}