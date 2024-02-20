package com.example.secondlab;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "secondLabServlet", value = "/secondLabServlet")
public class SecondLabServlet extends HttpServlet {

    private String HTML_START;
    private String HTML_END;

    // This function has been taken out from the first Lab work of the first semester first year without being modified.
    public static double firstFunction(double a, double b, double c, double d) {
        // Результат функції th(|b| * c) - tanh(|b| * c) - гіперболічний тангенс
        double thResult = Math.tanh(Math.abs(b) * c);

        // Результат функції log c (d) - логарфіму
        /*
         У Java функція log бере за основу 10.
         Але так як
         log a (b) = log c (b) / log c (a)
         То
        */
        double logResult = Math.log(d) / Math.log(c);

        // Результат кореню th / log
        double sqrtResult = Math.sqrt(thResult / logResult);

        // Рузльтат ділення 2 * с на а
        double divisionResult = (2 * c) / a;

        // Аюсолютне значення суми результату кореня та результату ділення
        double absoluteValue = Math.abs(divisionResult + sqrtResult);

        // Результат функції ln - логарифма з основою e
        double lnResult = Math.log(absoluteValue) / Math.log(Math.E);

        return lnResult;
    }
    public void init() {
        // Multi-line strings are Java 15+
        HTML_START = """
<html>
    <head>
        <title>
            Second Lab work for Programming
        </title>
    </head>
    <body>""";

        HTML_END = """
    </body>
</html>""";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Cookie aCookie = null, bCookie = null, cCookie = null, dCookie = null;
        for (Cookie cookie : request.getCookies()) {
            switch (cookie.getName()) {
                case "aCookie":
                    aCookie = cookie;
                    break;
                case "bCookie":
                    bCookie = cookie;
                    break;
                case "cCookie":
                    cCookie = cookie;
                    break;
                case "dCookie":
                    dCookie = cookie;
                    break;
            }
        }
        out.print(HTML_START);
        out.print("""
        <span>
            Enter four numbers that will be put into a calculation.
        </span>
        <br/>""");
        out.println("       <form action= \"secondLabServlet\" method=\"post\">");
        out.println("            <label for=\"aInput\">Enter A:</label>");
        out.println("            <br/>");
        out.println("            <input type=\"text\" id=\"aInput\" name=\"aInput\" " + (aCookie == null ? "" : "value=\"" + aCookie.getValue() + "\"") + "/>");
        out.println("            <br/>");
        out.println("            <label for=\"aInput\">Enter B:</label>");
        out.println("            <br/>");
        out.println("            <input type=\"text\" id=\"bInput\" name=\"bInput\" " + (bCookie == null ? "" : "value=\"" + bCookie.getValue() + "\"") + "/>");
        out.println("            <br/>");
        out.println("            <label for=\"aInput\">Enter C:</label>");
        out.println("            <br/>");
        out.println("            <input type=\"text\" id=\"cInput\" name=\"cInput\" " + (cCookie == null ? "" : "value=\"" + cCookie.getValue() + "\"") + "/>");
        out.println("            <br/>");
        out.println("            <label for=\"aInput\">Enter D:</label>");
        out.println("            <br/>");
        out.println("            <input type=\"text\" id=\"dInput\" name=\"dInput\" " + (dCookie == null ? "" : "value=\"" + dCookie.getValue() + "\"") + "/>");
        out.println("            <br/>");
        out.println("            <input type=\"submit\" value=\"Submit data\" />");
        out.println("       </form>");
        out.print(HTML_END);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(HTML_START);
        try {
            double aInput, bInput, cInput, dInput;
            String[] aInputStr = request.getParameterValues("aInput");
            // We do this because query parameters are also parsed into doPost, and we only want body parameters
            aInput = Double.parseDouble(aInputStr.length > 1 ? aInputStr[1] : aInputStr[0]);
            String[] bInputStr = request.getParameterValues("bInput");
            bInput = Double.parseDouble(bInputStr.length > 1 ? bInputStr[1] : bInputStr[0]);
            String[] cInputStr = request.getParameterValues("cInput");
            cInput = Double.parseDouble(cInputStr.length > 1 ? cInputStr[1] : cInputStr[0]);
            String[] dInputStr = request.getParameterValues("dInput");
            dInput = Double.parseDouble(dInputStr.length > 1 ? dInputStr[1] : dInputStr[0]);
            out.println("        <span>You have entered:</span><br/>");
            out.println("        <span><b>A</b>: " + aInput + "</span><br/>");
            out.println("        <span><b>B</b>: " + bInput + "</span><br/>");
            out.println("        <span><b>C</b>: " + cInput + "</span><br/>");
            out.println("        <span><b>D</b>: " + dInput + "</span><br/>");
            out.println("        <span>The function result is:</span><br/>");
            out.println("        <span>" + firstFunction(aInput, bInput, cInput, dInput) + "</span>");
            Cookie aCookie = new Cookie("aCookie", Double.toString(aInput));
            response.addCookie(aCookie);
            aCookie.setMaxAge(60 * 60 * 24 * 2);
            Cookie bCookie = new Cookie("bCookie", Double.toString(bInput));
            response.addCookie(bCookie);
            bCookie.setMaxAge(60 * 60 * 24 * 2);
            Cookie cCookie = new Cookie("cCookie", Double.toString(cInput));
            response.addCookie(cCookie);
            cCookie.setMaxAge(60 * 60 * 24 * 2);
            Cookie dCookie = new Cookie("dCookie", Double.toString(dInput));
            dCookie.setMaxAge(60 * 60 * 24 * 2);
            response.addCookie(dCookie);
        } catch (NumberFormatException exception) {
            out.println("        <span><b>There has been an error!</b></span><br/>");
            out.println("        <span>" + exception + "</span>");
        }
        out.print(HTML_END);
    }

    public void destroy() {

    }
}