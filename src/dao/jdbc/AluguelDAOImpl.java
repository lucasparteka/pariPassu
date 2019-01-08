package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import dao.AluguelDAO;
import dao.ClienteDAO;
import entidades.Aluguel;

public class AluguelDAOImpl implements AluguelDAO {

	@Override
	public void insert(Connection conn, Aluguel aluguel) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement(
				"insert into en_aluguel (id_aluguel, id_cliente, data_aluguel, valor) values (?, ?, ?, ?)");

		Integer idAluguel = this.getNextId(conn);

		myStmt.setInt(1, idAluguel);
		myStmt.setInt(2, aluguel.getCliente().getIdCliente());

		java.sql.Date sqlDate = new java.sql.Date(aluguel.getDataAluguel().getTime());
		myStmt.setDate(3, sqlDate);
		myStmt.setFloat(4, aluguel.getValor());

		myStmt.execute();
		conn.commit();

		for (int i = 0; i < aluguel.getFilmes().size(); i++) {

			PreparedStatement stat = conn
					.prepareStatement("insert into re_aluguel_filme (id_filme, id_aluguel) values (?, ?)");
			stat.setInt(1, aluguel.getFilmes().get(i).getIdFilme());
			stat.setInt(2, idAluguel);

			stat.execute();
			conn.commit();
		}

	}

	@Override
	public Integer getNextId(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select nextval('seq_en_aluguel')");
		ResultSet rs = myStmt.executeQuery();
		rs.next();
		return rs.getInt(1);
	}

	@Override
	public void edit(Connection conn, Aluguel aluguel) throws Exception {

		PreparedStatement myStmt = conn.prepareStatement(
				"UPDATE en_aluguel SET id_cliente = ?, data_aluguel = ?, valor = ? where id_aluguel = ?");

		

		myStmt.setInt(1, aluguel.getCliente().getIdCliente());

		java.sql.Date sqlDate = new java.sql.Date(aluguel.getDataAluguel().getTime());
		myStmt.setDate(2, sqlDate);
		myStmt.setFloat(3, aluguel.getValor());
		myStmt.setFloat(4, aluguel.getIdAluguel());

		myStmt.execute();
		conn.commit();
		
		PreparedStatement deleteRel = conn.prepareStatement("delete from re_aluguel_filme where id_aluguel = ?");

		deleteRel.setInt(1, aluguel.getIdAluguel());

		deleteRel.execute();
		conn.commit();

		for (int i = 0; i < aluguel.getFilmes().size(); i++) {

			PreparedStatement stat = conn
					.prepareStatement("insert into re_aluguel_filme (id_filme, id_aluguel) values (?, ?)");
			stat.setInt(1, aluguel.getFilmes().get(i).getIdFilme());
			stat.setInt(2, aluguel.getIdAluguel());

			stat.execute();
			conn.commit();
		}

	}

	@Override
	public void delete(Connection conn, Aluguel aluguel) throws Exception {
		
		PreparedStatement deleteRel = conn.prepareStatement("delete from re_aluguel_filme where id_aluguel = ?");

		deleteRel.setInt(1, aluguel.getIdAluguel());

		deleteRel.execute();
		conn.commit();
		
		PreparedStatement myStmt = conn.prepareStatement("delete from en_aluguel where id_aluguel = ?");

		myStmt.setInt(1, aluguel.getIdAluguel());

		myStmt.execute();
		conn.commit();

	}

	@Override
	public Aluguel find(Connection conn, Integer idAluguel) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select * from en_aluguel where id_aluguel = ?");

        myStmt.setInt(1, idAluguel);

        ResultSet myRs = myStmt.executeQuery();

        if (!myRs.next()) {
            return null;
        }
        ClienteDAO clienteDAO = new ClienteDAOImpl();
        Aluguel aluguel = new Aluguel();
		aluguel.setIdAluguel(myRs.getInt("id_aluguel"));
		aluguel.setCliente(clienteDAO.find(conn, myRs.getInt("id_cliente")));
		aluguel.setValor(myRs.getFloat("valor"));
		java.util.Date newDate = myRs.getTimestamp("data_aluguel");
		aluguel.setDataAluguel(newDate);
		
		return aluguel;
	}

	@Override
	public Collection<Aluguel> list(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select * from en_aluguel order by data_aluguel");
		ResultSet myRs = myStmt.executeQuery();
		Aluguel aluguel;
		Collection<Aluguel> items = new ArrayList<>();
		ClienteDAO clienteDAO = new ClienteDAOImpl();

		while (myRs.next()) {
			aluguel = new Aluguel();
			aluguel.setIdAluguel(myRs.getInt("id_aluguel"));
			aluguel.setCliente(clienteDAO.find(conn, myRs.getInt("id_cliente")));
			aluguel.setValor(myRs.getFloat("valor"));
			java.util.Date newDate = myRs.getTimestamp("data_aluguel");
			aluguel.setDataAluguel(newDate);

			items.add(aluguel);
		}

		return items;
	}

}
