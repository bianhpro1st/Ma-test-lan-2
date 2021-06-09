/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Article;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BaseInfo;
import model.ChosenArticle;

/**
 *
 * @author Win
 */
public class detail extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ArrayList<Article> top5 = new ArrayList<>();
            Article article = null;
            try {
                request.setAttribute("top1", BaseInfo.getBaseInfo(top5));
            } catch (Exception e) {
                request.setAttribute("top1", null);
                request.setAttribute("error", "Xin lỗi, đã có một vài lỗi xảy ra trong lúc lấy dữ liệu");
            }
            request.setAttribute("top5", top5);

            String rawId = request.getParameter("id");
            if (rawId != null) {
                try {
                    int id = Integer.parseInt(rawId);
                    try {
                        article = ChosenArticle.getChosenArticle(id);
                        if (article == null) {
                            request.setAttribute("message", "Bài viết bạn cần không có");
                        }
                    } catch (Exception e) {
                        request.setAttribute("error", "Xin lỗi, đã có một vài lỗi xảy ra trong lúc lấy dữ liệu");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("message", "Id phải là một số");
                }
            } else {
                request.setAttribute("error", "Hệ thống không tìm thấy id của bài viết");
            }

            request.setAttribute("article", article);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (Exception e) {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
