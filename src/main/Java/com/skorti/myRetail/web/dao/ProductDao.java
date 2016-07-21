package com.skorti.myRetail.web.dao;

import com.skorti.myRetail.web.model.Product;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by skorti on 10/28/15.
 */
@Repository
public class ProductDao extends AbstractJdbcTemplateDao {

    private String findProductSql;
    private String updateProductSql;


    public void setFindProductSql(String findProductSql) {
        this.findProductSql = findProductSql;
    }

    public void setUpdateProductSql(String updateProductSql) {
        this.updateProductSql = updateProductSql;
    }

    public Product findProduct(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        final List<Product> productList = new ArrayList<Product>();
        getJdbcTemplate().query(findProductSql, params, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setValue(rs.getDouble("value"));
                p.setCurrency_code(rs.getString("currency_code"));
                productList.add(p);

            }

        });
        return productList.get(0);
    }

    public Double findProductPrice(Integer id){
        return findProduct(id).getValue();
    }

    public Integer updateProduct(Integer id, Double value) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("value", value);

        final List<Product> productList = new ArrayList<Product>();
        int status = getJdbcTemplate().update(updateProductSql, params);
        return status;
    }
}
