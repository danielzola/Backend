package com.bat.velo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "vlo_dashboarditem")
public class DashboardItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    protected long id;
    
    @Column(unique = true)
    protected String name;
    
    @Column(name = "data_type")
    protected String dataType;
    
    @Column(name = "sql_query", columnDefinition = "TEXT")
    protected String sqlQuery;
    
    @Column(name = "target_class")
    protected String targetClass;
}
