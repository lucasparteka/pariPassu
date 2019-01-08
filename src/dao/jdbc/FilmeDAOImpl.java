package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import dao.FilmeDAO;
import entidades.Filme;

public class FilmeDAOImpl implements FilmeDAO{

	@Override
	public void insert(Connection conn, Filme filme) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("insert into en_filme (id_filme, data_lancamento, nome, descricao) values (?, ?, ?, ?)");

		java.sql.Date sqlDate = new java.sql.Date(filme.getDataLancamento().getTime());
        Integer idFilme = this.getNextId(conn);

        myStmt.setInt(1, idFilme);
        myStmt.setDate(2, sqlDate);
        myStmt.setString(3, filme.getNome());
        myStmt.setString(4, filme.getDescricao());

        myStmt.execute();
        conn.commit();

	}

	@Override
	public Integer getNextId(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select nextval('seq_en_filme')");
        ResultSet rs = myStmt.executeQuery();
        rs.next();
        return rs.getInt(1);
	}

	@Override
	public void edit(Connection conn, Filme filme) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("update en_filme SET data_lancamento = (?), nome = (?), descricao = (?) where id_filme = (?)");

		java.sql.Date sqlDate = new java.sql.Date(filme.getDataLancamento().getTime());
        myStmt.setDate(1,sqlDate);
        myStmt.setString(2, filme.getNome());
        myStmt.setString(3, filme.getDescricao());
        myStmt.setInt(4, filme.getIdFilme());

        myStmt.execute();
        conn.commit();
		
	}

	@Override
	public void delete(Connection conn, Integer idFilme) throws Exception {
		PreparedStatement deleteRel = conn.prepareStatement("delete from re_aluguel_filme where id_filme = ?");

		deleteRel.setInt(1, idFilme);

		deleteRel.execute();
		conn.commit();
		
		PreparedStatement myStmt = conn.prepareStatement("delete from en_filme where id_filme = ?");

        myStmt.setInt(1, idFilme);

        myStmt.execute();
        conn.commit();
		
	}

	@Override
	public Filme find(Connection conn, Integer idFilme) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select * from en_filme where id_filme = ?");

        myStmt.setInt(1, idFilme);

        ResultSet myRs = myStmt.executeQuery();

        if (!myRs.next()) {
            return null;
        }
        
        Filme filme = new Filme();
        filme.setIdFilme(myRs.getInt("id_filme"));
        
        java.util.Date newDate = myRs.getTimestamp("data_lancamento");
        filme.setDataLancamento(newDate);
        
        filme.setNome(myRs.getString("nome"));
        filme.setDescricao(myRs.getString("descricao"));
        
        return filme;
	}

	@Override
	public Collection<Filme> list(Connection conn) throws Exception {
		Filme filme;
		PreparedStatement myStmt = conn.prepareStatement("select * from en_filme order by nome");
        ResultSet myRs = myStmt.executeQuery();

        Collection<Filme> items = new ArrayList<>();

        while (myRs.next()) {
        	filme = new Filme();
            filme.setIdFilme(myRs.getInt("id_filme"));
            java.util.Date newDate = myRs.getTimestamp("data_lancamento");
            filme.setDataLancamento(newDate);
            filme.setNome(myRs.getString("nome"));
            filme.setDescricao(myRs.getString("descricao"));

            items.add(filme);
        }

        return items;
	}

}
