package project.model;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static project.model.ClientOperation.FIND;
import static project.model.ClientOperation.getClientOperation;


public class ClientOperationTest {

    @Test(expected = IllegalArgumentException.class)
    public void badHttpMethodTest() throws Exception {
        ServletWebRequest webRequest = prepareWebRequest(HttpMethod.HEAD);

        getClientOperation(webRequest);
    }

    @Test
    public void createOperationTest() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("someUri");
        when(request.getMethod()).thenReturn("POST");

        ServletWebRequest webRequest = new ServletWebRequest(request);

        ClientOperation operation = getClientOperation(webRequest);

        assertEquals(ClientOperation.CREATE, operation);
    }

    @Test
    public void loadOperationTest() throws Exception {
        ClientOperation operation = getClientOperation(prepareWebRequest(HttpMethod.GET));

        assertEquals(ClientOperation.LOAD, operation);
    }

    @Test
    public void updateOperationTest() throws Exception {
        ClientOperation operation = getClientOperation(prepareWebRequest(HttpMethod.PUT));

        assertEquals(ClientOperation.UPDATE, operation);
    }

    @Test
    public void deleteOperationTest() throws Exception {
        ClientOperation operation = getClientOperation(prepareWebRequest(HttpMethod.DELETE));

        assertEquals(ClientOperation.DELETE, operation);
    }

    @Test
    public void findOperationTest() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("someUri/" + ClientOperation.QUERY);
        when(request.getMethod()).thenReturn("POST");

        ServletWebRequest webRequest = new ServletWebRequest(request);

        ClientOperation operation = getClientOperation(webRequest);

        assertEquals(FIND, operation);
    }

    private ServletWebRequest prepareWebRequest(HttpMethod method) {
        ServletWebRequest webRequest = mock(ServletWebRequest.class);
        when(webRequest.getHttpMethod()).thenReturn(method);

        return webRequest;
    }

}