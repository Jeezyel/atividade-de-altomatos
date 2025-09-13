import javax.swing.*;
import java.awt.*;

public class PainelGrid extends JPanel {
    private JogoDaVida jogo;

    public PainelGrid(JogoDaVida jogo) {
        this.jogo = jogo;
        setBackground(Color.WHITE); //Fundo branco
    }
    public void setJogo(JogoDaVida jogo) {
        this.jogo = jogo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (jogo == null) return;

        int tamanhoCelula = 15; // Tamanho de cada quadradinho em pixels

        for (int i = 0; i < jogo.getLinhas(); i++) {
            for (int j = 0; j < jogo.getColunas(); j++) {
                // Se a célula estiver viva, pinta de preto
                if (jogo.getEstadoCelula(i, j) == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(j * tamanhoCelula, i * tamanhoCelula,
                            tamanhoCelula, tamanhoCelula);
                } else {
                    // Célula morta - fundo branco
                    g.setColor(Color.WHITE);
                    g.fillRect(j * tamanhoCelula, i * tamanhoCelula,
                            tamanhoCelula, tamanhoCelula);
                }
                // Desenha uma borda sutil
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(j * tamanhoCelula, i * tamanhoCelula,
                        tamanhoCelula, tamanhoCelula);
            }
        }
    }
}
