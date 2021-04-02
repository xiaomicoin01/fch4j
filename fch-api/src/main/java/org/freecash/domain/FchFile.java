package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Table(name = "fch_file")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class FchFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pid")
    private Integer pid;

    @Column(name="user_name",nullable=false)
    private String username;

    @Column(name="file_name",nullable=false)
    private String fileName;

    @Column(name="file_path",nullable=false)
    private String filePath;

    @Column(name = "create_date", nullable = false)
    @CreatedDate
    private Date createDate;
}
