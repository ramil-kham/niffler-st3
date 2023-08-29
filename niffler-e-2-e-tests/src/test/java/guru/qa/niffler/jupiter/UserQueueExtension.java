package guru.qa.niffler.jupiter;

import guru.qa.niffler.model.UserJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UserQueueExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UserQueueExtension.class);
    private static Map<User.UserType, Queue<UserJson>> usersQueue = new ConcurrentHashMap<>();

    static {
        Queue<UserJson> userWithFriends = new ConcurrentLinkedQueue<>();
        userWithFriends.add(bindUser("dima", "dima"));
        userWithFriends.add(bindUser("barsik", "12345"));
        usersQueue.put(User.UserType.WITH_FRIENDS, userWithFriends);

        Queue<UserJson> userInSent = new ConcurrentLinkedQueue<>();
        userInSent.add(bindUser("bee", "12345"));
        userInSent.add(bindUser("anna", "12345"));
        usersQueue.put(User.UserType.INVITATION_SENT, userInSent);

        Queue<UserJson> userInRc = new ConcurrentLinkedQueue<>();
        userInRc.add(bindUser("valentin", "12345"));
        userInRc.add(bindUser("pizzly", "12345"));
        usersQueue.put(User.UserType.INVITATION_RECEIVED, userInRc);
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        List<User> parameterAnnotation = new LinkedList<>();
        Map<User.UserType, UserJson> allCandidates = new HashMap<>();
        for (Method method : context.getRequiredTestClass().getDeclaredMethods()) {
            Parameter[] parameters = method.getParameters();
            if (method.isAnnotationPresent(BeforeEach.class)
                    || method.isAnnotationPresent(Test.class)
                    && Arrays.stream(parameters).anyMatch(p -> p.isAnnotationPresent(User.class))
            ) {
                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i].getType().isAssignableFrom(UserJson.class)) {
                        parameterAnnotation.add(parameters[i].getAnnotation(User.class));
                        User.UserType userType = parameterAnnotation.get(i).userType(); // получаем userType из аннотации
                        Queue<UserJson> usersQueueByType = usersQueue.get(userType); // пихаем в очередь userType из аннотации
                        UserJson candidateForTest = null;
                        while (candidateForTest == null) {
                            candidateForTest = usersQueueByType.poll();
                        }
                        candidateForTest.setUserType(userType);
                        allCandidates.put(userType, candidateForTest);
                    }
                }
                context.getStore(NAMESPACE).put(getAllureId(context), allCandidates);
            }
            break;
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Map<User.UserType, UserJson> usersFromTest = context.getStore(NAMESPACE).get(getAllureId(context), Map.class);
        if (usersFromTest != null) {
            usersFromTest.forEach((userType, userJson) -> usersQueue.get(userType).add(userJson));
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .isAssignableFrom(UserJson.class)
                && parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return (UserJson) extensionContext.getStore(NAMESPACE).get(getAllureId(extensionContext), Map.class)
                .get(parameterContext.getParameter().getAnnotation(User.class).userType());
    }

    private String getAllureId(ExtensionContext context) {
        AllureId allureId = context.getRequiredTestMethod().getAnnotation(AllureId.class);
        if (allureId == null) {
            throw new IllegalStateException("Annotation AllureId must be present");
        }
        return allureId.value();
    }

    private static UserJson bindUser(String username, String password) {
        UserJson user = new UserJson();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
