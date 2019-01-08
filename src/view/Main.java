package view;

import dao.AluguelDAO;
import dao.ClienteDAO;
import dao.FilmeDAO;
import dao.jdbc.AluguelDAOImpl;
import dao.jdbc.ClienteDAOImpl;
import dao.jdbc.FilmeDAOImpl;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", "admin");
			conn.setAutoCommit(false);

			// Demonstrar o funcionamento aqui
			ClienteDAO clienteDAO = new ClienteDAOImpl();
			AluguelDAO aluguelDAO = new AluguelDAOImpl();
			FilmeDAO filmeDAO = new FilmeDAOImpl();

			Filme filme;
			String dataString;
			DateFormat formatter;
			Date dateJava;
			int opcao = 1;
			while (opcao > 0) {
				opcao = Integer.parseInt(JOptionPane.showInputDialog(
						"PARIPASSU FLIX\n" + "1 - LOCA��O\n" + "2 - CLIENTES\n" + "3 - FILMES\n" + "0 - SAIR"));

				switch (opcao) {
				// loca��o
				case 1:
					Aluguel aluguel;
					ArrayList<Filme> filmesEscolhidos;
					int opcaoLocacoes = 1;
					while (opcaoLocacoes != 0) {
						opcaoLocacoes = Integer.parseInt(JOptionPane
								.showInputDialog("LOCA��O\n" + "1 - REGISTRAR LOCA��O\n" + "2 - LISTAR LOCA��ES\n"
										+ "3 - ALTERAR LOCA��O\n" + "4 - EXCLUIR LOCA��O\n" + "0 - SAIR"));

						switch (opcaoLocacoes) {

						case 1:
							Cliente cliente;
							aluguel = new Aluguel();
							filmesEscolhidos = new ArrayList<>();
							int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o id do cliente"));
							cliente = clienteDAO.find(conn, idCliente);
							if (cliente == null) {
								JOptionPane.showMessageDialog(null, "Cliente n�o encontrado");
							} else {
								aluguel.setCliente(cliente);
								dataString = JOptionPane.showInputDialog("Digite a data da loca��o\n ex: 10/10/2010");
								formatter = new SimpleDateFormat("MM/dd/yyyy");
								dateJava = (Date) formatter.parse(dataString);
								aluguel.setDataAluguel(dateJava);

								int opc = 0;
								do {
									int idFilmeEscolhido = Integer
											.parseInt(JOptionPane.showInputDialog("Digite o id do filme escolhido"));
									filme = filmeDAO.find(conn, idFilmeEscolhido);
									if (filme == null) {
										JOptionPane.showMessageDialog(null, "Filme n�o localizado");
									} else {

										filmesEscolhidos.add(filme);
									}
									opc = Integer.parseInt(JOptionPane
											.showInputDialog("Informar novo filme?\n" + "1 - SIM\n" + "2 - N�O"));
								} while (opc != 2);
								aluguel.setFilmes(filmesEscolhidos);
								aluguel.setValor(
										Float.parseFloat(JOptionPane.showInputDialog("Digite o valor da loca��o")));
								aluguelDAO.insert(conn, aluguel);
							}
							break;
						case 2:
							Collection<Aluguel> listAlugueis = new ArrayList<>();
							listAlugueis = aluguelDAO.list(conn);
							for (Aluguel totalAlugueis : listAlugueis) {
								System.out.println(totalAlugueis.toString());
							}
							break;
						case 3:
							filmesEscolhidos = new ArrayList<>();
							int idAluguel = Integer
									.parseInt(JOptionPane.showInputDialog("Digite o id do aluguel que ser� editado"));
							aluguel = aluguelDAO.find(conn, idAluguel);
							if (aluguel == null) {
								JOptionPane.showMessageDialog(null, "Aluguel n�o encontrado");
								break;
							} else {
								idCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o id do cliente"));
								cliente = clienteDAO.find(conn, idCliente);
								if (cliente == null) {
									JOptionPane.showMessageDialog(null, "Cliente n�o encontrado");
									break;
								} else {
									aluguel.setCliente(cliente);
									dataString = JOptionPane
											.showInputDialog("Digite a data da loca��o\n ex: 10/10/2010");
									formatter = new SimpleDateFormat("MM/dd/yyyy");
									dateJava = (Date) formatter.parse(dataString);
									aluguel.setDataAluguel(dateJava);
									aluguel.setValor(
											Float.parseFloat(JOptionPane.showInputDialog("Digite o valor da loca��o")));

									int opc = 0;
									do {
										int idFilmeEscolhido = Integer.parseInt(
												JOptionPane.showInputDialog("Digite o id do filme escolhido"));
										filme = filmeDAO.find(conn, idFilmeEscolhido);
										if (filme == null) {
											JOptionPane.showMessageDialog(null, "Filme n�o localizado");
										} else {
											filmesEscolhidos.add(filme);
										}
										opc = Integer.parseInt(JOptionPane.showInputDialog(
												"Informar novo filme do aluguel?\n" + "1 - SIM\n" + "2 - N�O"));
									} while (opc != 2);
									aluguel.setFilmes(filmesEscolhidos);
									aluguelDAO.edit(conn, aluguel);
								}
							}
							break;
						case 4:
							int idAluguelExclusao = Integer
									.parseInt(JOptionPane.showInputDialog("Informe o id do aluguel"));
							aluguel = aluguelDAO.find(conn, idAluguelExclusao);
							if (aluguel == null) {
								JOptionPane.showMessageDialog(null, "aluguel n�o encontrado");
							} else {
								aluguelDAO.delete(conn, aluguel);
								JOptionPane.showMessageDialog(null, "Aluguel excluido com sucesso");
							}
							break;
						case 0:
							break;
						default:
							JOptionPane.showMessageDialog(null, "Op��o inv�lida");
						}
					}
					break;
				// clientes
				case 2:
					Cliente cliente = new Cliente();
					int opcaoclientes = 1;
					while (opcaoclientes != 0) {
						opcaoclientes = Integer.parseInt(JOptionPane
								.showInputDialog("CLIENTES\n" + "1 - REGISTRAR CLIENTE\n" + "2 - LISTAR CLIENTES\n"
										+ "3 - ALTERAR CADASTRO CLIENTE\n" + "4 - EXCLUIR CLIENTE\n" + "0 - SAIR"));

						switch (opcaoclientes) {
						case 1:
							cliente.setNome(JOptionPane.showInputDialog("Informe o nome do cliente"));
							cliente.setIdCliente(clienteDAO.getNextId(conn));
							clienteDAO.insert(conn, cliente);
							break;
						case 2:
							Collection<Cliente> listClientes = new ArrayList<>();
							listClientes = clienteDAO.list(conn);
							for (Cliente totalClientes : listClientes) {
								System.out.println(totalClientes.toString());
							}
							break;
						case 3:
							int idCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite o id do cliente"));
							cliente = clienteDAO.find(conn, idCliente);
							if (cliente == null) {
								JOptionPane.showMessageDialog(null, "Cliente n�o encontrado");
							} else {
								cliente.setNome(JOptionPane.showInputDialog("Informe o nome do cliente"));
								clienteDAO.edit(conn, cliente);
								JOptionPane.showMessageDialog(null, "Informa��o alterada com sucesso");
							}
							break;
						case 4:
							int idExclusao = Integer.parseInt(JOptionPane.showInputDialog("Digite o id do cliente"));
							cliente = clienteDAO.find(conn, idExclusao);
							if (cliente == null) {
								JOptionPane.showMessageDialog(null, "Cliente n�o encontrado");
							} else {
								clienteDAO.delete(conn, idExclusao);
								JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso");
							}
							break;
						case 0:
							break;
						default:
							JOptionPane.showMessageDialog(null, "Op��o inv�lida");
						}
					}
					break;
				// filmes
				case 3:
					int opcaoFilmes = 1;
					while (opcaoFilmes != 0) {
						opcaoFilmes = Integer.parseInt(
								JOptionPane.showInputDialog("FILMES\n" + "1 - CADASTRAR FILME\n" + "2 - LISTAR FILMES\n"
										+ "3 - ALTERAR INFORMA��ES FILME\n" + "4 - EXCLUIR FILME\n" + "0 - SAIR"));

						switch (opcaoFilmes) {
						case 1:
							filme = new Filme();
							filme.setIdFilme(filmeDAO.getNextId(conn));

							dataString = JOptionPane
									.showInputDialog("Digite a data de lan�amento do filme\n ex: 10/10/2010");
							formatter = new SimpleDateFormat("MM/dd/yyyy");
							dateJava = (Date) formatter.parse(dataString);
							filme.setDataLancamento(dateJava);
							filme.setNome(JOptionPane.showInputDialog("Digite o nome do filme"));
							filme.setDescricao(JOptionPane.showInputDialog("Digite a descri��o"));
							filmeDAO.insert(conn, filme);
							JOptionPane.showMessageDialog(null, "Filme cadastrado com sucesso");
							break;
						case 2:
							Collection<Filme> listFilmes = new ArrayList<>();
							listFilmes = filmeDAO.list(conn);
							for (Filme totalFilmes : listFilmes) {
								System.out.println(totalFilmes.toString());
							}
							break;
						case 3:
							int idFilmeEdit = Integer.parseInt(JOptionPane.showInputDialog("Digite o id do filme"));
							filme = filmeDAO.find(conn, idFilmeEdit);
							if (filme == null) {
								JOptionPane.showMessageDialog(null, "Nenhum filme encontrado");
							} else {
								dataString = JOptionPane
										.showInputDialog("Digite a data de lan�amento do filme\n ex: 10/10/2010");
								formatter = new SimpleDateFormat("MM/dd/yyyy");
								dateJava = (Date) formatter.parse(dataString);
								filme.setDataLancamento(dateJava);
								filme.setNome(JOptionPane.showInputDialog("Digite o nome do filme"));
								filme.setDescricao(JOptionPane.showInputDialog("Digite a descri��o"));
								filmeDAO.edit(conn, filme);
								JOptionPane.showMessageDialog(null, "Informa��es alteradas com sucesso");
							}
							break;
						case 4:
							int idExclusao = Integer.parseInt(JOptionPane.showInputDialog("Digite o id do filme"));
							filme = filmeDAO.find(conn, idExclusao);
							if (filme == null) {
								JOptionPane.showMessageDialog(null, "Filme n�o encontrado");
							} else {
								filmeDAO.delete(conn, idExclusao);
								JOptionPane.showMessageDialog(null, "Filme excluido com sucesso");
							}
							break;
						case 0:
							break;
						default:
							JOptionPane.showMessageDialog(null, "Op��o inv�lida");
						}
					}
					break;
				case 0:
					break;
				default:
					JOptionPane.showMessageDialog(null, "Op��o inv�lida");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Fim do teste.");
	}
}