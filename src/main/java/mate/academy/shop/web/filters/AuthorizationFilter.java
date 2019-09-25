package mate.academy.shop.web.filters;

import static mate.academy.shop.model.Role.RoleName.ADMIN;
import static mate.academy.shop.model.Role.RoleName.USER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.shop.anotation.Inject;
import mate.academy.shop.model.Role;
import mate.academy.shop.model.User;
import mate.academy.shop.service.UserService;

public class AuthorizationFilter implements Filter {
    public static final String EMPTY_STRING = "";
    @Inject
    private static UserService userService;
    private Map<String, Role.RoleName> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/servlet/getAllUsers", ADMIN);
        protectedUrls.put("/allItemsAdmin", ADMIN);
        protectedUrls.put("/deleteItemAdmin", ADMIN);
        protectedUrls.put("/servlet/deleteOrder", USER);
        protectedUrls.put("/servlet/getAllItems", USER);
        protectedUrls.put("/servlet/bucket", USER);
        protectedUrls.put("/servlet/showAllOrders", USER);
        protectedUrls.put("/servlet/addToBucket", USER);
        protectedUrls.put("/servlet/deleteItemFromBucket", USER);
        protectedUrls.put("/servlet/makeOrders", USER);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            processUnAuthenticated(req, resp);
            return;
        }
        String requestedUrl = req.getRequestURI().replace(req.getContextPath(), EMPTY_STRING);
        Role.RoleName roleName = protectedUrls.get(requestedUrl);
        if (roleName == null) {
            processAuthenticated(req, resp, chain);
            return;
        }
        String token = null;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("MATE")) {
                token = cookie.getValue();
                break;
            }
        }
        if (token == null) {
            processUnAuthenticated(req, resp);
            return;
        } else {
            Optional<User> user = userService.getByToken(token);
            if (user.isPresent()) {
                if (verifyRole(user.get(), roleName)) {
                    processAuthenticated(req, resp, chain);
                    return;
                } else {
                    processDenied(req, resp);
                    return;
                }
            } else {
                processUnAuthenticated(req, resp);
                return;
            }

        }

    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles().stream()
                .anyMatch(x -> x.getRoleName().equals(roleName));
    }

    private void processDenied(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
    }

    private void processAuthenticated(HttpServletRequest req, HttpServletResponse resp,
                                      FilterChain chain) throws IOException, ServletException {
        chain.doFilter(req, resp);
    }

    private void processUnAuthenticated(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    public void destroy() {

    }
}
