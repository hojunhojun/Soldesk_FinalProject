package com.ung.recipetoyou.community;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="rty_com_like")
public class CommunityPostLike {
   @Id
   @SequenceGenerator(sequenceName = "rty_comlike_seq", name="rcs", allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="rcs")
   @Column(name="rcl_no")
   private Integer no;
   
   @Column(name="rcl_mem_id")
   private String memId;

   @Column(name="rcl_post_no")
   private Integer postNo;
   
   @Column(name="rcl_post_type")
   private Integer postType;
}
