package br.com.caelum.agenda.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.agenda.ConnectionFactory;
import br.com.caelum.agenda.modelo.Contato;

public class ContatoDao {
	private Connection connection;

	public ContatoDao() {
		try {
			this.connection = new ConnectionFactory().getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public ContatoDao(Connection connection) {
		this.connection = connection;
	}

	public void adiciona(Contato contato) {
		try {
			String sql = "insert into contatos (nome, email, endereco, dataNascimento) values (?,?,?,?)";
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getPassword());
			stmt.setString(3, contato.getEmail());
			stmt.setDate(4, new Date(contato.getBirthday().getTimeInMillis()));

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Contato> getLista() {
		try {
			List<Contato> contatos = new ArrayList<Contato>();
			PreparedStatement stmt = this.connection.prepareStatement("select * from contatos");

			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				Contato contato = new Contato();
				contato.setId(rs.getLong("id"));
				contato.setNome(rs.getString("nome"));
				contato.setPassword(rs.getString("email"));
				contato.setEmail(rs.getString("endereco"));

				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("dataNascimento"));
				contato.setBirthday(data);

				contatos.add(contato);
			}

			rs.close();
			stmt.close();

			return contatos;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void exclui(Contato contato) {
		String sql = "delete from contatos where id=?";
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setLong(1, contato.getId());
			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void atualiza(Contato contato) {
		String sql = "update contatos set nome = ?, email = ?, endereco = ?, dataNascimento = ? where id = ?";
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getPassword());
			stmt.setString(3, contato.getEmail());
			stmt.setDate(4, new java.sql.Date(contato.getBirthday().getTimeInMillis()));
			stmt.setLong(5, contato.getId());

			stmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
