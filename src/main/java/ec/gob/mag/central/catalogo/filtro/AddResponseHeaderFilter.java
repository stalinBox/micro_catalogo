package ec.gob.mag.central.catalogo.filtro;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
@WebFilter("/*")
public class AddResponseHeaderFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		Date myDate = new Date();

		httpServletResponse.setHeader("Validation-Consult-Header",
				"{\"objetivoConsulta\":\"" + httpServletRequest.getHeader("objetivoConsulta")
						+ "\",\"puntoConsulta\":\"" + httpServletRequest.getRequestURI() + "\",\"catalogoConsulta\":\""
						+ httpServletRequest.getContextPath() + "\",fecha\":\"" + myDate.toString() + "\"}");

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}