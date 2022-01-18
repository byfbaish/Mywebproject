package ysx.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import ysx.dbManger.DbManger;
import ysx.tool.reader;

@Path("/")
public class myApplication {

    @GET
    @Path("/bao-deposit/v1.2.0/installmentTimeDeposit/payout")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response post(@Context HttpServletRequest servletRequest, @Context HttpServletResponse servletResponse) {
        HttpServletRequest request = servletRequest;

        System.out.println("thist is my post" + request.getMethod());
        String body = "";
        try {
            body = reader.getString(request.getInputStream());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        String sqlString = "DELETE FROM MOCK_CONTROL";
        Connection connection = null;
        try {
            connection = DbManger.getConnection();

            // プリペアードステートメントを取得する。
            PreparedStatement ps = connection.prepareStatement(sqlString, ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            System.out.println("finally");
        }
        System.out.println("thist is my body:" + body);
        ResponseBuilder response = Response.status(Response.Status.OK);
        response.entity(body);

        return response.build();
    }

}
