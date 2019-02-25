package com.zsls.pojo;

import com.zsls.groups.Groups;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author zsls
 * @Description //TODO @NotNull://CharSequence, Collection, Map 和 Array 对象不能是 null, 但可以是空集（size = 0）。  
 * @NotEmpty://CharSequence, Collection, Map 和 Array 对象不能是 null 并且相关对象的 size 大于 0。  
 * @NotBlank://String 不是 null 且去除两端空白字符后的长度（trimmed length）大于 0
 * @Date 16:41 2019/2/25
 *
 * @Param
 * @return
 */
public class Book {

	@NotNull(message ="id 不允许为空",groups = Groups.Update.class)
    private Integer id;
	@NotBlank(message ="name 不允许为空",groups = Groups.Default.class)
	@Length(min = 2,max = 10,message = "name 长度必须在 {min}-{max}之间")
    private String name;
    @NotNull(message = "price 不允许为空",groups = Groups.Default.class)
    @DecimalMin(value = "0.1", message = "价格不能低于 {value}")
    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
