public class AlunoDAO {
    private final Connection conexao;

    public AlunoDAO() {
        this.conexao = DatabaseConfig.getConnection();
    }

    public void inserir(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO alunos (id, nome, cpf, data_nascimento, curso, termo, "
                   + "logradouro, bairro, municipio, uf, cep, telefone, email) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, aluno.getId());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, aluno.getCpf());
            stmt.setDate(4, Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(5, aluno.getCurso());
            stmt.setString(6, aluno.getTermo());
            stmt.setString(7, aluno.getLogradouro());
            stmt.setString(8, aluno.getBairro());
            stmt.setString(9, aluno.getMunicipio());
            stmt.setString(10, aluno.getUf());
            stmt.setString(11, aluno.getCep());
            stmt.setString(12, aluno.getTelefone());
            stmt.setString(13, aluno.getEmail());

            stmt.executeUpdate();
        }
    }
}