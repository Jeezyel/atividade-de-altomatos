import java.util.Random;

public class JogoDaVida {
    // Dimensões do grid
    private int linhas;
    private int colunas;
    private int[][] grid;
    // inicio do jogo
    public JogoDaVida(int linhas, int colunas) {
        // definino os limites do grid
        this.linhas = linhas;
        this.colunas = colunas;
        this.grid = new int[linhas][colunas];
        // inicializando o grid com valores aleatórios (0 ou 1)
        inicializarGrid();
    }

    
    private void inicializarGrid() {
        // gerando valores aleatórios para o grid
        Random random = new Random();

        //pecorro o grid
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                //add o valor 0 ou 1 aleatoriamente
                grid[i][j] = random.nextInt(2);
            }
        }
    }

    //conta os vizinhos vivos de uma célula
    private int contarVizinhosVivos(int x, int y) {

        // contando os vizinhos vivos
        int vivos = 0;

        //pecorro o grid
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                // ignorando a célula atual
                if (!(i == 0 && j == 0)) {
                    int linha = (x + i + linhas) % linhas;
                    int coluna = (y + j + colunas) % colunas;
                    vivos += grid[linha][coluna];
                }
            }
        }
        return vivos;
    }

    // calcula a próxima geração do grid
    public void proximaGeracao() {
        int[][] novoGrid = new int[linhas][colunas];

        //pecorro o grid
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                int vizinhos = contarVizinhosVivos(i, j);

                if (grid[i][j] == 1) {
                    // Célula viva
                    if (vizinhos == 2 || vizinhos == 3) {
                        novoGrid[i][j] = 1;
                    } else {
                        novoGrid[i][j] = 0;
                    }
                } else {
                    // Célula morta
                    if (vizinhos == 3) {
                        novoGrid[i][j] = 1;
                    } else {
                        novoGrid[i][j] = 0;
                    }
                }
            }
        }

        grid = novoGrid;
    }

    // mostra o grid no console
    public void mostrarGrid() {
        //pecorro o grid
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                // imprimindo a célula (█ para viva, espaço para morta)
                System.out.print(grid[i][j] == 1 ? "█" : " ");
            }
            // pulando para a próxima linha
            System.out.println();
        }
        // pulando para a próxima linha
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {
        JogoDaVida jogo = new JogoDaVida(20, 50);
        for (int geracao = 0; geracao < 50; geracao++) {
            System.out.println("Geração: " + geracao);
            jogo.mostrarGrid();
            jogo.proximaGeracao();
            Thread.sleep(300);
        }
    }
}
