package br.com.programaestagio;

import br.com.programaestagio.model.*;
import br.com.programaestagio.repository.*;
import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FrmAluno extends javax.swing.JFrame {
    
    // Variáveis de componentes (geradas automaticamente pelo NetBeans)
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1, jLabel2, /*... todos os labels... */;
    private javax.swing.JTextField txtNome, txtEndereco, /*... todos os campos... */;
    
    public FrmAluno() {
        initComponents();
        configurarAcoesAdicionais(); // Configura ações extras
    }

    // Método gerado automaticamente pelo NetBeans - não altere manualmente
    private void initComponents() {
        // Código gerado pelo NetBeans (mantenha como está)
        // ... todo o código de inicialização de componentes ...
    }

    /**
     * Configura ações adicionais que não são geradas pelo NetBeans
     */
   private void configurarAcoesAdicionais() {
        configurarMascaras();
        adicionarValidacoes();
    }

    private void configurarMascaras() {
        try {
            javax.swing.text.MaskFormatter cpfMask = new javax.swing.text.MaskFormatter("###.###.###-##");
            cpfMask.install(txtCpf);
            
            javax.swing.text.MaskFormatter cepMask = new javax.swing.text.MaskFormatter("#####-###");
            cepMask.install(txtCep);
            
        } catch (java.text.ParseException e) {
            JOptionPane.showMessageDialog(this, "Erro ao configurar máscaras: " + e.getMessage());
        }
    }

    private void adicionarValidacoes() {
        txtCpf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (!validarCPF(txtCpf.getText())) {
                    JOptionPane.showMessageDialog(FrmAluno.this, 
                        "CPF inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    txtCpf.requestFocus();
                }
            }
        });
    }

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {
        if (!validarFormulario()) {
            return;
        }
        
     try {
            Aluno aluno = criarAlunoFromForm();
            Estagio estagio = criarEstagioFromForm();
            SupervisorAcademico supervisor = criarSupervisorFromForm();
            
            aluno.setEstagio(estagio);
            aluno.setSupervisorAcademico(supervisor);
            
            salvarDados(aluno, estagio, supervisor);
            
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
            limparFormulario();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao cadastrar: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Valida todos os campos antes do cadastro
     */
    private boolean validarFormulario() {
        if (txtNome.getText().trim().isEmpty() ||
            txtCpf.getText().trim().isEmpty() ||
            txtCurso.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Preencha todos os campos obrigatórios!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (!validarCPF(txtCpf.getText())) {
            JOptionPane.showMessageDialog(this, "CPF inválido!");
            txtCpf.requestFocus();
            return false;
        }
        
        return true;
    }
  private Aluno criarAlunoFromForm() {
        return new Aluno.Builder()
            .comId(txtIdAluno.getText())
            .comNome(txtNome.getText())
            .comCpf(txtCpf.getText().replaceAll("[^0-9]", ""))
            .comRg(txtRg.getText())
            .comDataNascimento(LocalDate.parse(txtDataNascimento.getText(), 
                DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            .comCurso(txtCurso.getText())
            .comTermo(txtTermo.getText())
            .comLogradouro(txtEndereco.getText())
            .comBairro(txtBairro.getText())
            .comMunicipio(txtMunicipio.getText())
            .comUf(txtUf.getText())
            .comCep(txtCep.getText().replaceAll("[^0-9]", ""))
            .comTelefone(txtTelefone.getText())
            .comEmail(txtEmail.getText())
            .build();
    }

    private Estagio criarEstagioFromForm() {
        return new Estagio.Builder()
            .comAreaEstagio(txtAreaEstagio.getText())
            .comDataInicio(LocalDate.parse(txtDataInicio.getText(), 
                DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            .comDataTermino(LocalDate.parse(txtDataTermino.getText(), 
                DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            .build();
    }

    private SupervisorAcademico criarSupervisorFromForm() {
        return new SupervisorAcademico.Builder()
            .comNome(txtSupervisorAcademico.getText())
            .build();
    }

    private void salvarDados(Aluno aluno, Estagio estagio, SupervisorAcademico supervisor) throws Exception {
        try {
            AlunoRepository alunoRepo = new AlunoRepository();
            EstagioRepository estagioRepo = new EstagioRepository();
            SupervisorRepository supervisorRepo = new SupervisorRepository();
            
            supervisorRepo.salvar(supervisor);
            alunoRepo.salvar(aluno);
            estagioRepo.salvar(estagio);
            
        } catch (Exception e) {
            throw new Exception("Erro ao persistir dados: " + e.getMessage());
        }
    }

    private void limparFormulario() {
        txtNome.setText("");
        txtEndereco.setText("");
        // Limpe os outros campos aqui...
    }

    private boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        return cpf.length() == 11 && !cpf.matches(cpf.charAt(0) + "{11}");
    }

    // Métodos de ação dos componentes (gerados automaticamente)
    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void txtEnderecoActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    // ... (mantenha os outros métodos de ação gerados pelo NetBeans) ...

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new FrmAluno().setVisible(true);
        });
    }
    
    // Variáveis de componentes (geradas automaticamente)
    // ... (mantenha as declarações de variáveis no final) ...
}
    /**
     * Configura máscaras para CPF, CEP, etc.
     */
    private void configurarMascaras() {
        try {
            // Exemplo para CPF
            javax.swing.text.MaskFormatter cpfMask = new javax.swing.text.MaskFormatter("###.###.###-##");
            cpfMask.install(txtCpf);
            
            // Exemplo para CEP
            javax.swing.text.MaskFormatter cepMask = new javax.swing.text.MaskFormatter("#####-###");
            cepMask.install(txtCep);
            
            // Adicione máscaras para outros campos conforme necessário
            
        } catch (java.text.ParseException e) {
            JOptionPane.showMessageDialog(this, "Erro ao configurar máscaras: " + e.getMessage());
        }
    }

    /**
     * Adiciona validações em tempo real
     */
    private void adicionarValidacoes() {
        // Valida CPF quando o campo perde o foco
        txtCpf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (!validarCPF(txtCpf.getText())) {
                    JOptionPane.showMessageDialog(FrmAluno.this, 
                        "CPF inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    txtCpf.requestFocus();
                }
            }
        });
        
        // Adicione outros validadores conforme necessário
    }

    /**
     * Ação do botão OK - principal método de cadastro
     *
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtEndereco = new javax.swing.JTextField();
        txtBairro = new javax.swing.JTextField();
        txtCep = new javax.swing.JTextField();
        txtTelefone = new javax.swing.JTextField();
        txtMunicipio = new javax.swing.JTextField();
        txtCpf = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtRg = new javax.swing.JTextField();
        txtIdAluno = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtDataNascimento = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUf = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtCurso = new javax.swing.JTextField();
        txtTermo = new javax.swing.JTextField();
        btnOk = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtSupervisorAcademico = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtAreaEstagio = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtDataTermino = new javax.swing.JTextField();
        txtDataInicio = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nome");

        jLabel2.setText("Endereço");

        jLabel3.setText("CEP");

        jLabel4.setText("Bairro");

        jLabel6.setText("Município");

        jLabel7.setText("Telefone");

        jLabel9.setText("RG");

        jLabel10.setText("ID");

        jLabel11.setText("DADOS DO ALUNO");

        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        txtEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEnderecoActionPerformed(evt);
            }
        });

        txtCep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCepActionPerformed(evt);
            }
        });

        jLabel12.setText("CPF");

        jLabel13.setText("Email");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel14.setText("Data de Nascimento");

        jLabel5.setText("UF");

        txtUf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUfActionPerformed(evt);
            }
        });

        jLabel15.setText("Curso");

        jLabel16.setText("Termo");

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        jLabel8.setText("S:upervisor acadêmico:");

        txtSupervisorAcademico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupervisorAcademicoActionPerformed(evt);
            }
        });

        jLabel17.setText("Área do Estágio:");

        txtAreaEstagio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAreaEstagioActionPerformed(evt);
            }
        });

        jLabel18.setText("Data do Início:");

        jLabel19.setText("Data do término:");

        txtDataTermino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataTerminoActionPerformed(evt);
            }
        });

        txtDataInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataInicioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtRg, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUf, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMunicipio, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                                    .addComponent(txtEndereco)
                                    .addComponent(txtNome, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(24, 24, 24))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAreaEstagio)
                                    .addComponent(txtSupervisorAcademico)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(txtDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDataTermino, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(jLabel11))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTermo, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdAluno, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtRg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdAluno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14)
                    .addComponent(txtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtTermo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtSupervisorAcademico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAreaEstagio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txtDataTermino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnOk)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    private void txtEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEnderecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEnderecoActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtUfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUfActionPerformed

    private void txtSupervisorAcademicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupervisorAcademicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupervisorAcademicoActionPerformed

    private void txtAreaEstagioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAreaEstagioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAreaEstagioActionPerformed

    private void txtDataTerminoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataTerminoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataTerminoActionPerformed

    private void txtDataInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataInicioActionPerformed

    private void txtCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCepActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:                                   
    try {
        // Criar e configurar o Aluno
        Aluno aluno = new Aluno.Builder()
            .comId(txtIdAluno.getText())
            .comNome(txtNome.getText())
            .comCpf(txtCpf.getText())
            .comDataNascimento(LocalDate.parse(txtDataNascimento.getText()))
            .comCurso(txtCurso.getText())
            .comTermo(txtTermo.getText())
            .comLogradouro(txtEndereco.getText())
            .comBairro(txtBairro.getText())
            .comMunicipio(txtMunicipio.getText())
            .comUf(txtUf.getText())
            .comCep(txtCep.getText())
            .comTelefone(txtTelefone.getText())
            .comEmail(txtEmail.getText())
            .build();

        // Criar e configurar o Supervisor Acadêmico
        SupervisorAcademico supervisor = new SupervisorAcademico.Builder()
            .comNome(txtSupervisorAcademico.getText())
            // Adicione outros campos necessários
            .build();

        // Criar e configurar o Estágio
        Estagio estagio = new Estagio.Builder()
            .comAluno(aluno)
            .comAreaEstagio(txtAreaEstagio.getText())
            .comDataInicio(LocalDate.parse(txtDataInicio.getText()))
            .comDataTermino(LocalDate.parse(txtDataTermino.getText()))
            .comSupervisorAcademico(supervisor)
            // Configure outros campos necessários
            .build();

        // Persistir os dados
        AlunoRepository alunoRepo = new AlunoRepository();
        EstagioRepository estagioRepo = new EstagioRepository();
        SupervisorRepository supervisorRepo = new SupervisorRepository();

        // Iniciar transação (se necessário)
        supervisorRepo.salvar(supervisor);
        alunoRepo.salvar(aluno);
        estagioRepo.salvar(estagio);

        JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
        this.dispose(); // Fecha o formulário após cadastro

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Erro ao cadastrar: " + e.getMessage(), 
            "Erro", 
            JOptionPane.ERROR_MESSAGE);
    }
}
   
    private static class txtIdAluno {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtIdAluno() {
        }
    }

    private static class txtNome {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtNome() {
        }
    }

    private static class txtEndereco {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtEndereco() {
        }
    }

    private static class txtBairro {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtBairro() {
        }
    }

    private static class txtCep {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtCep() {
        }
    }

    private static class txtMunicipio {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtMunicipio() {
        }
    }

    private static class txtUf {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtUf() {
        }
    }

    private static class txtCpf {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtCpf() {
        }
    }

    private static class txtCurso {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtCurso() {
        }
    }

    private static class txtTelefone {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtTelefone() {
        }
    }

    private static class txtEmail {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtEmail() {
        }
    }

    private static class JOptionPane {

        private static void showMessageDialog(FrmAluno aThis, String string) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public JOptionPane() {
        }
    }

    private static class txtAreaEstagio {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtAreaEstagio() {
        }
    }

    private static class txtDataInicio {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtDataInicio() {
        }
    }

    private static class txtDataTermino {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtDataTermino() {
        }
    }

    private static class txtSupervisorAcademico {

        private static String getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public txtSupervisorAcademico() {
        }
    }
            
            
    }//GEN-LAST:event_btnOkActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the wINDOWS look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmAluno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAluno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAluno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAluno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAluno().setVisible(true);
           }
        };
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtAreaEstagio;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtCep;
    private javax.swing.JTextField txtCpf;
    private javax.swing.JTextField txtCurso;
    private javax.swing.JTextField txtDataInicio;
    private javax.swing.JTextField txtDataNascimento;
    private javax.swing.JTextField txtDataTermino;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtIdAluno;
    private javax.swing.JTextField txtMunicipio;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtRg;
    private javax.swing.JTextField txtSupervisorAcademico;
    private javax.swing.JTextField txtTelefone;
    private javax.swing.JTextField txtTermo;
    private javax.swing.JTextField txtUf;
    // End of variables declaration//GEN-END:variables
}
