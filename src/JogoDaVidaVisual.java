import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JogoDaVidaVisual extends JFrame {
    private JogoDaVida jogo; // O motor
    private PainelGrid painel; // Mostra o que está acontecendo
    private Timer timer; //Controla a velocidade
    private int geracao = 0;

    // UI extras
    private JLabel labelGeracao; //Mostra a contagem
    private JCheckBox chkSemLimite;
    private JSpinner spinMaxGeracoes; // Limite definido pelo usuário (HVB)

    public JogoDaVidaVisual() {

        jogo = new JogoDaVida(20, 50);

        // Configura a janela
        setTitle("Autômato Celular - Jogo da Vida");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        int largura = 50 * 15 + 30; // Colunas * tamanhoCelula + margens
        int altura = 20 * 15 + 100; // Linhas * tamanhoCelula + barra/decoração
        setSize(new Dimension(largura, altura));
        setLocationRelativeTo(null);
        // setVisible(true);  // deixa para o final, após montar toda a UI

        // Painel superior com informações
        JPanel painelSuperior = new JPanel();
        labelGeracao = new JLabel("Geração: 0");

        // cria os controles de limite ANTES de adicionar ao painel
        spinMaxGeracoes = new JSpinner(new SpinnerNumberModel(100, 1, 1_000_000, 1));
        chkSemLimite = new JCheckBox("Sem limite", true); // Se marcado, ignora limite (HVB)

        // Botões de controle
        JButton btnIniciar = new JButton("Iniciar");
        JButton btnPausar = new JButton("Pausar");
        JButton btnReset = new JButton("Reset");

        // adiciona na ordem desejada
        painelSuperior.add(btnIniciar);
        painelSuperior.add(btnPausar);
        painelSuperior.add(btnReset);
        painelSuperior.add(new JSeparator(JSeparator.VERTICAL));
        painelSuperior.add(new JLabel("Máx. Gerações:"));
        painelSuperior.add(spinMaxGeracoes);
        painelSuperior.add(chkSemLimite);
        painelSuperior.add(new JSeparator(JSeparator.VERTICAL));
        painelSuperior.add(labelGeracao);

        // decide onde quer exibir a barra (SUL ou NORTE); aqui mantém SUL como você fez
        add(painelSuperior, BorderLayout.SOUTH);

        // Cria o painel onde o grid será desenhado
        painel = new PainelGrid(jogo);
        add(painel, BorderLayout.CENTER);

        // Configura o timer para atualizar a cada 300ms
        timer = new Timer(300, (ActionEvent e) -> {
            jogo.proximaGeracao();
            geracao++;
            labelGeracao.setText("Geração: " + geracao);

            if (!chkSemLimite.isSelected()) {
                int max = (Integer) spinMaxGeracoes.getValue();
                // parar exatamente quando alcançar o limite
                if (geracao >= max) {
                    timer.stop();
                    labelGeracao.setText("Geração: " + geracao + " (limite atingido)");
                }
            }
            painel.repaint();
        });

        // Configura os botões
        btnIniciar.addActionListener(ae -> {
            // Se limite já foi atingido, evitar iniciar sem reset
            if (!chkSemLimite.isSelected()) {
                int max = (Integer) spinMaxGeracoes.getValue();
                if (geracao >= max) {
                    JOptionPane.showMessageDialog(this,
                            "Limite de gerações já foi atingido. Use Reset para recomeçar.",
                            "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            if (!timer.isRunning()) timer.start();
        });

        btnPausar.addActionListener(ae -> {
            if (timer.isRunning()) timer.stop();
        });

        btnReset.addActionListener(e -> resetarSimulacao());

        // só agora exibe a janela
        setVisible(true);
    }

    private void resetarSimulacao() {
        if (timer.isRunning()) timer.stop();
        jogo = new JogoDaVida(20, 50); // Cria novo jogo
        painel.setJogo(jogo); // Atualiza o painel com o novo jogo
        geracao = 0;
        labelGeracao.setText("Geração: 0");
        painel.repaint();
    }

    public static void main(String[] args) {
        // Garante que a interface seja criada na thread correta
        SwingUtilities.invokeLater(JogoDaVidaVisual::new);
    }
}
