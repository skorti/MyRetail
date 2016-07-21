package com.skorti.myRetail.web.controller;

import com.skorti.myRetail.web.dao.ProductDao;
import com.skorti.myRetail.web.model.Product;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;



@Service
@Produces({MediaType.APPLICATION_JSON })
@Path("/services")
@Scope("request")
public class BaseController {

    @Autowired
    private ProductDao productDao;

    @Context
    UriInfo uriInfo;

    @Context
    HttpServletRequest request;

    final String PRODUCT_NAME_URL = "https://api.target.com/products/v3/13860428?fields=descriptions&amp;" +
            "id_type=TCIN&amp;key=43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz";

    Client client;


    private Client initWebResourceClient(){

        if(client == null) {
            DefaultClientConfig dcc = new DefaultClientConfig();
            client = Client.create(dcc);
        }

        return client;
    }



    private String getProductNameFromWebResource(Integer id){
        MultivaluedMap<String,String> queryParams = uriInfo.getQueryParameters();
        if(client == null) {
            initWebResourceClient();
        }
        WebResource resource = client.resource(PRODUCT_NAME_URL);

        ClientResponse response = resource
                .queryParams(queryParams)
                .entity(id.toString(), MediaType.APPLICATION_XML_TYPE)
                .post(ClientResponse.class);

        String entity = response.getEntity(String.class);
        return entity;
    }

    @GET
    @Path("/products/{id}")
    public Product findProduct(@PathParam("id") Integer id){

        String productName = getProductNameFromWebResource(id);
        Double productPrice = productDao.findProductPrice(id);
        Product product = new Product();
        product.setName(productName);
        product.setId(id);
        product.setCurrency_code("USD");
        product.setValue(productPrice);
        return product;
    }

    @POST
    @Path("/products/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Integer updateProduct(final String jsonStr) throws Exception{
        JSONObject jsonObject = new JSONObject(jsonStr);

        Integer id = jsonObject.getInt("id");
        Double value = jsonObject.getDouble("value");
        return productDao.updateProduct(id, value);

    }
}
