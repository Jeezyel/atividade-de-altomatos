import java.util.Random;

public class JogoDaVida {
    private int linhas;
    private int colunas;
    private int[][] grid;

    public JogoDaVida(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.grid = new int[linhas][colunas];
        inicializarGrid();
    }

    
    private void inicializarGrid() {
        Random random = new Random();
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                grid[i][j] = random.nextInt(2); 
            }
        }
    }

    
    private int contarVizinhosVivos(int x, int y) {
        int vivos = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0)) { 
                    int linha = (x + i + linhas) % linhas;   
                    int coluna = (y + j + colunas) % colunas;
                    vivos += grid[linha][coluna];
                }
            }
        }
        return vivos;
    }

    
    public void proximaGeracao() {
        int[][] novoGrid = new int[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                int vizinhos = contarVizinhosVivos(i, j);

                if (grid[i][j] == 1) {
                    
                    if (vizinhos == 2 || vizinhos == 3) {
                        novoGrid[i][j] = 1;
                    } else {
                        novoGrid[i][j] = 0;
                    }
                } else {
                    
                    if (vizinhos == 3) {
                        novoGrid[i][j] = 1;
                    }
                }
            }
        }

        grid = novoGrid;
    }

    
    public void mostrarGrid() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(grid[i][j] == 1 ?"█" : " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    
    public static void main(String[] args) throws InterruptedException {
        JogoDaVida jogo = new JogoDaVida(20, 40); 
        for (int geracao = 0; geracao < 50; geracao++) { 
            System.out.println("Geração: " + geracao);
            jogo.mostrarGrid();
            jogo.proximaGeracao();
            Thread.sleep(300); 
        }
    }
}
