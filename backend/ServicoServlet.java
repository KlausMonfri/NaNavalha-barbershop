package servlet;

import com.google.gson.Gson;
import dao.AgendamentoDAO;
import model.Agendamento;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.List;

@WebServlet("/agendamentos")
public class AgendamentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            String clienteIdParam = req.getParameter("clienteId");
            List<Agendamento> lista;

            if (clienteIdParam != null && !clienteIdParam.isEmpty()) {
                int clienteId = Integer.parseInt(clienteIdParam);
                lista = agendamentoDAO.listarPorCliente(clienteId);
            } else {
                lista = agendamentoDAO.listar();
            }

            resp.getWriter().write(gson.toJson(lista));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            int clienteId  = Integer.parseInt(req.getParameter("clienteId"));
            int barbeiroId = Integer.parseInt(req.getParameter("barbeiroId"));
            int servicoId  = Integer.parseInt(req.getParameter("servicoId"));
            String dataHora = req.getParameter("dataHora");
            agendamentoDAO.inserir(clienteId, barbeiroId, servicoId, dataHora);
            resp.getWriter().write("{\"mensagem\":\"Agendamento criado com sucesso!\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String dataHora = req.getParameter("dataHora");
            String status = req.getParameter("status");

            if (dataHora == null || dataHora.isEmpty()) {
                dataHora = "2025-01-01 00:00:00";
            }

            agendamentoDAO.atualizar(id, dataHora, status);
            resp.getWriter().write("{\"mensagem\":\"Agendamento atualizado com sucesso!\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            agendamentoDAO.deletar(id);
            resp.getWriter().write("{\"mensagem\":\"Agendamento deletado com sucesso!\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write("{\"erro\":\"" + e.getMessage() + "\"}");
        }
    }
}