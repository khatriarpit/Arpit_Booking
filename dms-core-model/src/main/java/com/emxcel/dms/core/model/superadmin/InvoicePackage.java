package com.emxcel.dms.core.model.superadmin;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;

import javax.persistence.*;

/**
 * Created by root on 1/12/17.
 */
@Entity
@Table(name="invoice_package",schema = SchemaConstant.DMS_SCHEMA)
public class InvoicePackage extends DMSEntity<Long,InvoicePackage> {

    /*jjj*/
    private static final long serialVersionUID = 5891478805658108595L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "INVOICEPACKAGE_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Column(name="category_name")
    private String categoryname;


    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
