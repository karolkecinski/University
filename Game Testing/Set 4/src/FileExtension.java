import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;

import java.io.FileNotFoundException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileExtension implements ParameterResolver
{
    @Retention(RetentionPolicy.RUNTIME)
    public @interface File
    {
        String path();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == String.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        File file = parameterContext.getParameter().getAnnotation(File.class);
        String filepath = file.path();
        java.io.File realfile = new java.io.File(filepath);
        Scanner sc = null;
        try
        {
            sc = new Scanner(realfile);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        StringBuilder response = new StringBuilder();
        while(sc.hasNext())
        {
            response.append(sc.nextLine());
        }
        sc.close();
        return response.toString();
    }
}
