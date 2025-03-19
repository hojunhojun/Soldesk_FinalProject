package com.ung.recipetoyou.recipe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "rty_member_recipe")
public class MemberRecipe {
	@Id
	@SequenceGenerator(sequenceName = "rty_member_recipe_seq", name="rmrs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rmrs")
	@Column(name = "rmr_no")
	private Integer no;

	@Column(name = "rmr_member_id")
    private String memberId;  // 회원 ID

    @Column(name = "rmr_recipe_id")
    private Integer recipeId;  // 레시피 ID

}
