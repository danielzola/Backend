package com.bat.velo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "vlo_magentotrx_metadata")
public class MagentotrxMetadataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected long id;

    @Column(name = "file_name")
    protected String fileName;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "Asia/Jakarta")
    @Column(name = "created_date")
    protected Date createdDate;

    @Column(name = "upload_by")
    protected String uploadBy;

    @Column(name = "generated_id")
    protected String generatedId;

}
