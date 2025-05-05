import java.util.Random;
import java.util.Scanner;

public class SistemaMatricula {
    public static void main(String[] args) {
        Scanner leia = new Scanner(System.in);
        Random aleatorio = new Random();

        Aluno[] vetorAlunos = new Aluno[5];
        Disciplina[] vetorDisciplinas = new Disciplina[3];

        for (int i = 0; i < vetorAlunos.length; i++) {
            vetorAlunos[i] = new Aluno();
            vetorAlunos[i].endereco = new Endereco();
            vetorAlunos[i].disciplinasAluno = new Disciplina[5];
        }

        for (int j = 0; j < vetorDisciplinas.length; j++) {
            vetorDisciplinas[j] = new Disciplina();
        }

        init(vetorAlunos, vetorDisciplinas, aleatorio);

        int opcao;
        boolean sair = false;
        while (!sair) {
            menu();
            System.out.println(">>> INSIRA UMA OPÇÃO: ");
            opcao = leia.nextInt();
            while (opcao < 1 || opcao > 9) {
                System.out.println(">>> INSIRA UMA OPÇÃO VÁLIDA: ");
                opcao = leia.nextInt();
            }
            switch (opcao) {
                case 1:
                    vetorDisciplinas = cadastrarDisciplina(vetorDisciplinas, leia);
                    break;
                case 2:
                    vetorDisciplinas = removerDisciplina(vetorDisciplinas, vetorAlunos, leia);
                    break;
                case 3:
                    listarDisciplinas(vetorDisciplinas, leia);
                    break;
                case 4:
                    vetorAlunos = cadastrarAluno(vetorAlunos, leia);
                    break;
                case 5:
                    alterarDadosDeAluno(vetorAlunos, leia);
                    break;
                case 6:
                    listarAlunos(vetorAlunos, leia);
                    break;
                case 7:
                    matricularAlunoEmUmaDisciplina(vetorAlunos, vetorDisciplinas, leia);
                    break;
                case 8:
                    listarAsMatriculas(vetorAlunos, vetorDisciplinas);
                    break;
                case 9:
                    sair = true;
                    break;
            }

        }
    }

    public static void menu() {
        System.out.println("=========== SISTEMA DE MATRICULA ===========");
        System.out.println("1. Cadastrar disciplina");
        System.out.println("2. Remover disciplina");
        System.out.println("3. Listar disciplinas");
        System.out.println("4. Cadastrar aluno");
        System.out.println("5. Alterar os dados de aluno");
        System.out.println("6. Listar alunos");
        System.out.println("7. Matricular aluno em uma disciplina");
        System.out.println("8. Listar as matrículas");
        System.out.println("9. Sair");
    }

    public static void init(Aluno[] vetorAlunos, Disciplina[] vetorDisciplinas, Random aleatorio) {

        // INICIALIZAR AS TRES PRIMEIRAS DISCIPLINAS
        String[] nomesDisciplinas = { "POO", "BD", "APS", "PW2" };
        String[] nomeProfessores = { "Luciano", "Lucas Pantuza", "Márcia", "Marcelo Balbino" };

        Disciplina cadastroDisciplina = new Disciplina();

        for (int j = 0; j < 3; j++) {
            cadastroDisciplina.codigo = j + 1;
            String auxNome = nomesDisciplinas[aleatorio.nextInt(nomesDisciplinas.length)];
            while (true) {
                if (!verificarNomeDisciplinaDuplicado(vetorDisciplinas, auxNome)) {
                    break;
                } else {
                    auxNome = nomesDisciplinas[aleatorio.nextInt(nomesDisciplinas.length)];
                }
            }
            cadastroDisciplina.nome = auxNome;
            cadastroDisciplina.ano = aleatorio.nextInt(3) + 1;
            cadastroDisciplina.nomeDoProfessor = nomeProfessores[aleatorio.nextInt(nomeProfessores.length)];

            inserirDisciplina(vetorDisciplinas, cadastroDisciplina, j);

        }

        // INICIALIZAR OS TRES PRIMEIROS ALUNOS
        String[] nomesAlunos = { "Carlos", "Letícia", "Victor", "Gabriel", "Maria" };
        String[] ruaEndereco = { "Rua 1", "Rua 2", "Rua 3", "Rua 4", "Rua 5" };
        String[] bairrosEndereco = { "Centro", "Sul", "Norte", "Leste", "Oeste" };
        String[] complementosEndereco = { "Apto 1", "Apto 2", "Apto 3", "Apto 4", "Apto 5" };
        String[] cepEndereco = { "00000-000", "11111-111", "22222-222", "33333-333", "44444-444" };
        String[] cidadeEndereco = { "Cidade 1", "Cidade 2", "Cidade 3", "Cidade 4", "Cidade 5" };
        String[] estadoEndereco = { "Estado 1", "Estado 2", "Estado 3", "Estado 4", "Estado 5" };
        String[] paisesEndereco = { "País 1", "País 2", "País 3", "País 4", "País 5" };

        Aluno cadastroAluno = new Aluno();
        cadastroAluno.endereco = new Endereco();

        for (int i = 0; i < 5; i++) {
            cadastroAluno.codigo = i + 1;
            cadastroAluno.nome = nomesAlunos[aleatorio.nextInt(nomesAlunos.length)];

            while (true) {
                if (!verificarNomeAlunoDuplicado(vetorAlunos, cadastroAluno.nome)) {
                    break;
                } else {
                    cadastroAluno.nome = nomesAlunos[aleatorio.nextInt(nomesAlunos.length)];
                }
            }

            cadastroAluno.endereco.rua = ruaEndereco[aleatorio.nextInt(ruaEndereco.length)];
            cadastroAluno.endereco.numero = aleatorio.nextInt(100);
            cadastroAluno.endereco.bairro = bairrosEndereco[aleatorio.nextInt(bairrosEndereco.length)];
            cadastroAluno.endereco.complemento = complementosEndereco[aleatorio.nextInt(complementosEndereco.length)];
            cadastroAluno.endereco.cep = cepEndereco[aleatorio.nextInt(cepEndereco.length)];
            cadastroAluno.endereco.cidade = cidadeEndereco[aleatorio.nextInt(cidadeEndereco.length)];
            cadastroAluno.endereco.estado = estadoEndereco[aleatorio.nextInt(estadoEndereco.length)];
            cadastroAluno.endereco.pais = paisesEndereco[aleatorio.nextInt(paisesEndereco.length)];

            inserirAluno(vetorAlunos, cadastroAluno, i);
        }
    }

    public static Disciplina[] cadastrarDisciplina(Disciplina[] vetorDisciplinas, Scanner leia) {
        vetorDisciplinas = aumentarTamanhoVetorDisciplinas(vetorDisciplinas);
        int indice = vetorDisciplinas.length - 1;

        Disciplina cadastroDisciplina = new Disciplina();

        System.out.println(">>> INSIRA O CÓDIGO DA DISCIPLINA: ");
        cadastroDisciplina.codigo = leia.nextInt();
        leia.nextLine();
        while (true) {
            if (!verificarCodDisciplinaDuplicado(vetorDisciplinas, cadastroDisciplina.codigo)) {
                break;
            } else {
                System.out.println(">>> CÓDIGO REPETIDO! TENTE NOVAMENTE: ");
                cadastroDisciplina.codigo = leia.nextInt();
                leia.nextLine();
            }
        }

        System.out.println(">>> INSIRA O NOME DA DISCIPLINA: ");
        cadastroDisciplina.nome = leia.nextLine();
        leia.nextLine();
        while (true) {
            if (!verificarNomeDisciplinaDuplicado(vetorDisciplinas, cadastroDisciplina.nome)) {
                break;
            } else {
                System.out.println(">>> NOME REPETIDO! TENTE NOVAMENTE: ");
                cadastroDisciplina.nome = leia.nextLine();
                leia.nextLine();
            }
        }

        System.out.println(">>> INSIRA O ANO DA DISCIPLINA: ");
        cadastroDisciplina.ano = leia.nextInt();
        leia.nextLine();
        System.out.println(">>> INSIRA O NOME DO PROFESSOR: ");
        cadastroDisciplina.nomeDoProfessor = leia.nextLine();
        leia.nextLine();

        inserirDisciplina(vetorDisciplinas, cadastroDisciplina, indice);

        System.out.println(">>> DISCIPLINA CADASTRADA COM SUCESSO!");
        return vetorDisciplinas;
    }

    public static Aluno[] cadastrarAluno(Aluno[] vetorAluno, Scanner leia) {
        vetorAluno = aumentarTamanhoVetorAluno(vetorAluno);
        int indice = vetorAluno.length - 1;

        Aluno cadastroAluno = new Aluno();

        cadastroAluno.codigo = indice + 1;

        System.out.println(">>> INSIRA O NOME DO ALUNO: ");
        cadastroAluno.nome = leia.nextLine();
        leia.nextLine();
        while (true) {
            if (!verificarNomeAlunoDuplicado(vetorAluno, cadastroAluno.nome)) {
                break;
            } else {
                System.out.println(">>> NOME REPETIDO! TENTE NOVAMENTE: ");
                cadastroAluno.nome = leia.nextLine();
                leia.nextLine();
            }
        }

        cadastroAluno.endereco = new Endereco();

        System.out.println(">>> INSIRA O RUA DO ALUNO: ");
        cadastroAluno.endereco.rua = leia.nextLine();
        leia.nextLine();

        System.out.println(">>> INSIRA O NÚMERO DA CASA DO ALUNO: ");
        cadastroAluno.endereco.numero = leia.nextInt();
        leia.nextLine();

        System.out.println(">>> INSIRA O BAIRRO DO ALUNO: ");
        cadastroAluno.endereco.bairro = leia.nextLine();
        leia.nextLine();

        System.out.println(">>> INSIRA O COMPLEMENTO DO ENDEREÇO DO ALUNO: ");
        cadastroAluno.endereco.complemento = leia.nextLine();

        System.out.println(">>> INSIRA O CEP DO ALUNO: ");
        cadastroAluno.endereco.cep = leia.nextLine();
        leia.nextLine();

        System.out.println(">>> INSIRA A CIDADE DO ALUNO: ");
        cadastroAluno.endereco.cidade = leia.nextLine();
        leia.nextLine();

        System.out.println(">>> INSIRA O ESTADO DO ALUNO: ");
        cadastroAluno.endereco.estado = leia.nextLine();
        leia.nextLine();

        System.out.println(">>> INSIRA O PAÍS DO ALUNO: ");
        cadastroAluno.endereco.pais = leia.nextLine();
        leia.nextLine();

        cadastroAluno.quantidadeDeDisciplinasMatriculadas = 0;
        cadastroAluno.disciplinasAluno = new Disciplina[vetorAluno[indice].quantidadeDeDisciplinasMatriculadas];
        cadastroAluno.notas = new double[vetorAluno[indice].quantidadeDeDisciplinasMatriculadas];

        for (int i = 0; i < cadastroAluno.notas.length; i++) {
            cadastroAluno.notas[i] = 0.0;
        }

        inserirAluno(vetorAluno, cadastroAluno, indice);

        System.out.println(">>> ALUNO CADASTRADO COM SUCESSO!");
        return vetorAluno;
    }

    public static void inserirAluno(Aluno[] vetorAluno, Aluno aluno, int indice) {
        vetorAluno[indice] = aluno;
    }

    public static void inserirDisciplina(Disciplina[] vetorDisciplinas, Disciplina disciplina, int indice) {
        vetorDisciplinas[indice] = disciplina;
    }

    public static Disciplina[] aumentarTamanhoVetorDisciplinas(Disciplina[] vetorDisciplinas) {
        int novoTamanho = vetorDisciplinas.length + 1;
        Disciplina[] novoVetorDisciplinas = new Disciplina[novoTamanho];

        for (int i = 0; i < vetorDisciplinas.length; i++) {
            novoVetorDisciplinas[i] = vetorDisciplinas[i];
        }

        novoVetorDisciplinas[vetorDisciplinas.length] = new Disciplina();

        return novoVetorDisciplinas;
    }

    public static Aluno[] aumentarTamanhoVetorAluno(Aluno[] vetorAluno) {
        int novoTamanho = vetorAluno.length + 1;
        Aluno[] novoVetorAluno = new Aluno[novoTamanho];

        for (int i = 0; i < vetorAluno.length; i++) {
            novoVetorAluno[i] = vetorAluno[i];
        }

        novoVetorAluno[vetorAluno.length] = new Aluno();

        return novoVetorAluno;
    }

    public static double[] aumentarTamanhoVetorNotas(double[] notas) {
        int novoTamanho = notas.length + 1;
        double[] novoVetorNotas = new double[novoTamanho];

        for (int i = 0; i < notas.length; i++) {
            novoVetorNotas[i] = notas[i];
        }
        novoVetorNotas[notas.length] = 0.0;
        return novoVetorNotas;
    }

    public static Disciplina[] removerDisciplina(Disciplina[] vetorDisciplinas, Aluno[] vetorAlunos, Scanner leia) {
        listarDisciplinas(vetorDisciplinas, leia);
        System.out.println();

        System.out.println(">>> INSIRA O CÓDIGO DA DISCIPLINA A SER REMOVIDA: ");
        int codigoDisciplina = leia.nextInt();
        leia.nextLine();

        int indice = buscarDisciplinaNoVetor(vetorDisciplinas, codigoDisciplina);

        for (int i = indice; i < vetorDisciplinas.length - 1; i++) {
            vetorDisciplinas[i] = vetorDisciplinas[i + 1];
        }
        Disciplina[] novoVetorDisciplinas = new Disciplina[vetorDisciplinas.length - 1];

        for (int i = 0; i < novoVetorDisciplinas.length; i++) {
            novoVetorDisciplinas[i] = new Disciplina();
            novoVetorDisciplinas[i] = vetorDisciplinas[i];
        }

        int indiceRemover = buscarDisciplinaNoVetor(vetorAlunos[indice].disciplinasAluno, codigoDisciplina);

        if (indiceRemover != -1) {
            for (int i = 0; i < vetorAlunos.length; i++) {
                vetorAlunos[i].disciplinasAluno = removerDisciplinaDeAluno(vetorAlunos[i].disciplinasAluno,
                        indiceRemover);
                vetorAlunos[i].notas = removerNotaDeDisciplina(vetorAlunos[i].notas, indiceRemover);
            }
        }

        System.out.println(">>> DISCIPLINA REMOVIDA COM SUCESSO!");

        return novoVetorDisciplinas;
    }

    public static Disciplina[] removerDisciplinaDeAluno(Disciplina[] disciplinasAluno, int indiceRemover) {
        if (disciplinasAluno == null || disciplinasAluno.length == 0) {
            System.out.println("O ALUNO NÃO ESTÁ MATRICULADO NESSA DISCIPLINA!");
            return disciplinasAluno;
        }

        Disciplina[] novoVetorDisciplinas = new Disciplina[disciplinasAluno.length - 1];
        for (int k = indiceRemover; k < disciplinasAluno.length - 1; k++) {
            disciplinasAluno[k] = disciplinasAluno[k + 1];
        }

        for (int j = 0; j < novoVetorDisciplinas.length; j++) {
            novoVetorDisciplinas[j] = disciplinasAluno[j];
        }

        return novoVetorDisciplinas;
    }

    public static double[] removerNotaDeDisciplina(double[] notas, int indiceRemover) {

        if (notas == null || notas.length == 0 || indiceRemover == -1) {
            return notas;
        }

        double[] novoVetorNotas = new double[notas.length - 1];
        for (int k = indiceRemover; k < notas.length - 1; k++) {
            notas[k] = notas[k + 1];
        }

        for (int j = 0; j < novoVetorNotas.length; j++) {
            novoVetorNotas[j] = notas[j];
        }

        return novoVetorNotas;
    }

    public static void listarDisciplinas(Disciplina[] vetorDisciplinas, Scanner leia) {
        System.out.println("==================== DISCIPLINAS ====================");
        System.out.printf("%-10s %-20s %-8s %-20s%n", "CÓDIGO", "NOME", "ANO", "PROFESSOR");
        System.out.println("---------------------------------------------------");

        for (int i = 0; i < vetorDisciplinas.length; i++) {
            if (vetorDisciplinas[i] != null) {
                System.out.printf("%-10d %-20s %-8d %-20s%n",
                        vetorDisciplinas[i].codigo,
                        vetorDisciplinas[i].nome,
                        vetorDisciplinas[i].ano,
                        vetorDisciplinas[i].nomeDoProfessor);
            }
        }
        System.out.println("===================================================");
        System.out.println("PRESSIONE ENTER PARA CONTINUAR... ");
        int enter = leia.nextInt();
        leia.nextLine();
    }

    public static void alterarDadosDeAluno(Aluno[] vetorAluno, Scanner leia) {
        listarAlunos(vetorAluno, leia);
        System.out.println();

        System.out.println(">>> INSIRA O CÓDIGO DO ALUNO QUE DESEJA ALTERAR: ");
        int codAlunoEscolhido = leia.nextInt();
        leia.nextLine();

        boolean encontrado;
        int indiceAluno = -1;

        while (true) {
            encontrado = false;
            for (int i = 0; i < vetorAluno.length; i++) {
                if (vetorAluno[i].codigo == codAlunoEscolhido) {
                    encontrado = true;
                    indiceAluno = i;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("ALUNO NÃO ENCONTRADO! INSIRA OUTRO CÓDIGO: ");
                codAlunoEscolhido = leia.nextInt();
                leia.nextLine();
            } else {
                break;
            }
        }

        System.out.println(">>> INSIRA O QUE VOCÊ DESEJA ALTERAR: ");
        System.out.println("1 - NOME");
        System.out.println("2 - ENDEREÇO");
        System.out.println("3 - DISCIPLINAS MATRICULADAS");
        System.out.println("4 - NOTAS");
        System.out.println("5 - SAIR");

        int opcao = leia.nextInt();
        leia.nextLine();

        while (opcao < 1 || opcao > 5) {
            System.out.println("OPÇÃO INVÁLIDA! TENTE NOVAMENTE: ");
            opcao = leia.nextInt();
            leia.nextLine();
        }

        switch (opcao) {
            case 1:
                System.out.println(">>> INSIRA O NOVO NOME DO ALUNO: ");
                vetorAluno[indiceAluno].nome = leia.nextLine();
                leia.nextLine();
                while (true) {
                    encontrado = false;
                    for (int j = 0; j < vetorAluno.length - 1; j++) {
                        if (vetorAluno[j].nome.equalsIgnoreCase(vetorAluno[indiceAluno].nome)) {
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        break;
                    } else {
                        System.out.println(">>> NOME REPETIDO! TENTE NOVAMENTE: ");
                        vetorAluno[indiceAluno].nome = leia.nextLine();
                        leia.nextLine();
                    }
                }
                System.out.println(">>> NOME ALTERADO COM SUCESSO!");
                break;
            case 2:
                System.out.println(">>> INSIRA QUAL PARTE DO ENDEREÇO VOCÊ DESEJA ALTERAR: ");
                System.out.println("1 - RUA");
                System.out.println("2 - NÚMERO DA CASA");
                System.out.println("3 - BAIRRO");
                System.out.println("4 - COMPLEMENTO");
                System.out.println("5 - CEP");
                System.out.println("6 - CIDADE");
                System.out.println("7 - ESTADO");
                System.out.println("8 - PAÍS");
                System.out.println("9 - SAIR");

                int opcaoEndereco = leia.nextInt();
                leia.nextLine();

                switch (opcaoEndereco) {
                    case 1:
                        System.out.println(">>> INSIRA A NOVA RUA DO ALUNO: ");
                        vetorAluno[indiceAluno].endereco.rua = leia.nextLine();
                        leia.nextLine();
                        System.out.println(">>> RUA ALTERADA COM SUCESSO!");
                        break;
                    case 2:
                        System.out.println(">>> INSIRA O NOVO NÚMERO DA CASA DO ALUNO: ");
                        vetorAluno[indiceAluno].endereco.numero = leia.nextInt();
                        leia.nextLine();
                        System.out.println(">>> NÚMERO DA CASA ALTERADO COM SUCESSO!");
                        break;
                    case 3:
                        System.out.println(">>> INSIRA O NOVO BAIRRO DO ALUNO: ");
                        vetorAluno[indiceAluno].endereco.bairro = leia.nextLine();
                        leia.nextLine();
                        System.out.println(">>> BAIRRO ALTERADO COM SUCESSO!");
                        break;
                    case 4:
                        System.out.println(">>> INSIRA O NOVO COMPLEMENTO DA CASA DO ALUNO: ");
                        vetorAluno[indiceAluno].endereco.complemento = leia.nextLine();
                        leia.nextLine();
                        System.out.println(">>> COMPLEMENTO ALTERADO COM SUCESSO!");
                        break;
                    case 5:
                        System.out.println(">>> INSIRA O NOVO CEP DO ALUNO: ");
                        vetorAluno[indiceAluno].endereco.cep = leia.nextLine();
                        leia.nextLine();
                        System.out.println(">>> CEP ALTERADO COM SUCESSO!");
                        break;
                    case 6:
                        System.out.println(">>> INSIRA O NOVO CIDADE DO ALUNO: ");
                        vetorAluno[indiceAluno].endereco.cidade = leia.nextLine();
                        leia.nextLine();
                        System.out.println(">>> CIDADE ALTERADA COM SUCESSO!");
                    case 7:
                        System.out.println(">>> INSIRA O NOVO ESTADO DO ALUNO: ");
                        vetorAluno[indiceAluno].endereco.estado = leia.nextLine();
                        leia.nextLine();
                        System.out.println(">>> ESTADO ALTERADO COM SUCESSO!");
                        break;
                    case 8:
                        System.out.println(">>> INSIRA O NOVO PAÍS DO ALUNO: ");
                        vetorAluno[indiceAluno].endereco.pais = leia.nextLine();
                        leia.nextLine();
                        System.out.println(">>> PAÍS ALTERADO COM SUCESSO!");
                        break;
                }
            case 3:
                System.out.println(">>> ESSAS SÃO AS DISCIPLINAS QUE O ALUNO ESTÁ MATRICULADAS: ");
                listarDisciplinas(vetorAluno[indiceAluno].disciplinasAluno, leia);
                System.out.println();
                System.out.println(">>> INSIRA O CÓDIGO DA DISCIPLINA QUE DESEJA REMOVER: ");
                int codDisciplinaEscolhida = leia.nextInt();
                leia.nextLine();

                for (int i = 0; i < vetorAluno[indiceAluno].disciplinasAluno.length; i++) {
                    if (vetorAluno[indiceAluno].disciplinasAluno[i].codigo == codDisciplinaEscolhida) {
                        vetorAluno[indiceAluno].disciplinasAluno[i] = new Disciplina();
                        vetorAluno[indiceAluno].disciplinasAluno = removerDisciplinaDeAluno(
                                vetorAluno[indiceAluno].disciplinasAluno, codDisciplinaEscolhida);
                        vetorAluno[indiceAluno].quantidadeDeDisciplinasMatriculadas--;
                        System.out.println(">>> DISCIPLINA REMOVIDA COM SUCESSO!");
                        break;
                    }
                }
                break;
            case 4:
                for (int i = 0; i < vetorAluno[indiceAluno].notas.length; i++) {
                    System.out.println("DISCIPLINA " + (i + 1) + ": " + vetorAluno[indiceAluno].disciplinasAluno[i].nome
                            + " (" + vetorAluno[indiceAluno].disciplinasAluno[i].codigo + ")");
                    System.out.println("NOTA " + (i + 1) + ": " + vetorAluno[indiceAluno].notas[i]);
                }
                System.out.println(">>> INSIRA O CÓDIGO DA DISCIPLINA QUE VOCÊ DESEJA ALTERAR A NOTA DESEJA ALTERAR: ");
                int codigoDisciplina = leia.nextInt();
                leia.nextLine();

                int indiceDisciplina = -1;

                while (true) {
                    encontrado = false;
                    for (int i = 0; i < vetorAluno[indiceAluno].disciplinasAluno.length; i++) {
                        if (vetorAluno[indiceAluno].disciplinasAluno[i].codigo == codigoDisciplina) {
                            encontrado = true;
                            indiceDisciplina = i;
                            break;
                        }
                    }

                    if (!encontrado) {
                        System.out.println("ALUNO NÃO ENCONTRADO! INSIRA OUTRO CÓDIGO: ");
                        codigoDisciplina = leia.nextInt();
                        leia.nextLine();
                    } else {
                        break;
                    }
                }

                System.out.println(">>> INSIRA A NOVA NOTA DA DISCIPLINA: ");
                vetorAluno[indiceAluno].notas[indiceDisciplina] = leia.nextDouble();
                leia.nextLine();

                while (vetorAluno[indiceAluno].notas[indiceDisciplina] < 0
                        || vetorAluno[indiceAluno].notas[indiceDisciplina] > 10) {
                    System.out.println("INSIRA UMA NOTA VÁLIDA: ");
                    vetorAluno[indiceAluno].notas[indiceDisciplina] = leia.nextDouble();
                    leia.nextLine();
                }

                System.out.println(">>> NOTA ALTERADA COM SUCESSO!");

            case 5:
                menu();
        }

    }

    public static void matricularAlunoEmUmaDisciplina(Aluno[] vetorAluno, Disciplina[] vetorDisciplinas, Scanner leia) {
        listarAlunos(vetorAluno, leia);
        System.out.println();

        System.out.println(">>> INSIRA O CÓDIGO DO ALUNO QUE DESEJA MATRICULAR EM UMA DISCIPLINA: ");
        int codAlunoEscolhido = leia.nextInt();
        leia.nextLine();

        boolean encontrado;
        int indiceAluno;

        // primeiro ve se o aluno existe
        while (true) {
            encontrado = false;
            indiceAluno = -1;
            for (int i = 0; i < vetorAluno.length; i++) {
                if (vetorAluno[i].codigo == codAlunoEscolhido) {
                    encontrado = true;
                    indiceAluno = i;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("ALUNO NÃO ENCONTRADO! INSIRA OUTRO CÓDIGO: ");
                codAlunoEscolhido = leia.nextInt();
                leia.nextLine();
            } else {
                break;
            }
        }

        System.out.println();

        listarDisciplinas(vetorDisciplinas, leia);
        System.out.println();

        System.out.println("INSIRA O CÓDIGO DA DISCIPLINA QUE DESEJA MATRICULAR O ALUNO: ");
        int codDisciplinaEscolhida = leia.nextInt();
        leia.nextLine();

        // primeiro ve se a disciplina existe
        boolean encontrada = false;
        boolean jaMatriculado = false;
        int indiceDisciplina = -1;
        for (int i = 0; i < vetorDisciplinas.length; i++) {
            if (vetorDisciplinas[i].codigo == codDisciplinaEscolhida) {
                encontrada = true;
                indiceDisciplina = i;
                break;
            }
        }

        if (encontrada) {
            // agora ve se o aluno ja nao ta na disciplina
            for (int j = 0; j < vetorDisciplinas.length; j++) {
                jaMatriculado = false;
                if (vetorAluno[indiceAluno].disciplinasAluno[j].codigo == codDisciplinaEscolhida) {
                    // se o codigo for igual entao ele ja ta matriculado nessa disciplina
                    jaMatriculado = true;
                    System.out.println("O ALUNO JÁ ESTÁ MATRICULADO NESSA DISCIPLINA!");
                    break;
                }
            }
            if (!jaMatriculado) {
                vetorAluno[indiceAluno].disciplinasAluno = aumentarTamanhoVetorDisciplinas(
                        vetorAluno[indiceAluno].disciplinasAluno);
                vetorAluno[indiceAluno].notas = aumentarTamanhoVetorNotas(vetorAluno[indiceAluno].notas);
                vetorAluno[indiceAluno].disciplinasAluno[indiceDisciplina] = new Disciplina();
                vetorAluno[indiceAluno].disciplinasAluno[indiceDisciplina] = vetorDisciplinas[indiceDisciplina];
                vetorAluno[indiceAluno].quantidadeDeDisciplinasMatriculadas++;
                System.out.println("ALUNO MATRICULADO COM SUCESSO!");
            }
        }

    }

    public static void listarAlunos(Aluno[] vetorAluno, Scanner leia) {
        System.out.println();

        Aluno[] vetorAlunoOrdenado = new Aluno[vetorAluno.length];
        for (int i = 0; i < vetorAluno.length; i++) {
            vetorAlunoOrdenado[i] = new Aluno();
            vetorAluno[i].media = calcularMedia(vetorAluno[i].notas);
            vetorAlunoOrdenado[i] = vetorAluno[i];
        }
        ordenarVetor(vetorAlunoOrdenado);

        System.out.println("COMO VOCÊ QUER LISTAR OS ALUNOS?");
        System.out.println("1 - Listar pela ordem de cadastro");
        System.out.println("2 - Listar pela ordem decrescente da média das notas");

        int opcao = leia.nextInt();
        leia.nextLine();
        while (opcao < 1 || opcao > 2) {
            System.out.println("OPÇÃO INVÁLIDA! TENTE NOVAMENTE: ");
            opcao = leia.nextInt();
            leia.nextLine();
        }

        if (opcao == 1) {
            System.out.println("==================== ALUNOS ====================");
            System.out.println(
                    "CÓDIGO          NOME            ENDEREÇO              DISCIPLINAS              QUANTIDADE DE DISCIPLINAS                 NOTAS");

            for (int i = 0; i < vetorAluno.length; i++) {
                if (vetorAluno[i] != null) {
                    System.out.println(vetorAluno[i].codigo + "            " +
                            vetorAluno[i].nome + "      " +
                            vetorAluno[i].endereco.rua + "      " + vetorAluno[i].endereco.numero + "      "
                            + vetorAluno[i].endereco.bairro + "      " + vetorAluno[i].endereco.complemento + "      "
                            + vetorAluno[i].endereco.cep + "      " + vetorAluno[i].endereco.cidade + "      "
                            + vetorAluno[i].endereco.estado + "      " + vetorAluno[i].endereco.pais);
                    for (int j = 0; j < vetorAluno[i].disciplinasAluno.length; j++) {
                        System.out.println(vetorAluno[i].disciplinasAluno[j].nome);
                    }
                    System.out.println(vetorAluno[i].quantidadeDeDisciplinasMatriculadas);
                    System.out.println(vetorAluno[i].media);
                    System.out.println();
                } else {
                    System.out.println("NENHUM ALUNO CADASTRADO!");
                }
            }
        } else {

            System.out.println("==================== ALUNOS ====================");
            System.out.println(
                    "CÓDIGO          NOME            ENDEREÇO              DISCIPLINAS              QUANTIDADE DE DISCIPLINAS                 NOTAS");

            for (int i = 0; i < vetorAlunoOrdenado.length; i++) {
                if (vetorAlunoOrdenado[i] != null) {
                    System.out.println(vetorAlunoOrdenado[i].codigo + "            " +
                            vetorAlunoOrdenado[i].nome + "      " +
                            vetorAlunoOrdenado[i].endereco.rua + "      " + vetorAlunoOrdenado[i].endereco.numero
                            + "      " + vetorAlunoOrdenado[i].endereco.bairro + "      "
                            + vetorAlunoOrdenado[i].endereco.complemento + "      " + vetorAlunoOrdenado[i].endereco.cep
                            + "      " + vetorAlunoOrdenado[i].endereco.cidade + "      "
                            + vetorAlunoOrdenado[i].endereco.estado + "      " + vetorAlunoOrdenado[i].endereco.pais);
                    for (int j = 0; j < vetorAlunoOrdenado[i].disciplinasAluno.length; j++) {
                        System.out.println(vetorAlunoOrdenado[i].disciplinasAluno[j].nome);
                    }
                    System.out.println(vetorAlunoOrdenado[i].quantidadeDeDisciplinasMatriculadas);
                    System.out.println(vetorAlunoOrdenado[i].media);
                    System.out.println();
                }
            }

        }

    }

    public static void listarAsMatriculas(Aluno[] vetorAluno, Disciplina[] vetorDisciplinas) {
        System.out.println("==================== MATRICULAS ====================");

        for (int i = 0; i < vetorDisciplinas.length; i++) {
            System.out.println("DISCIPLINA: " + vetorDisciplinas[i].nome);
            for (int j = 0; j < vetorAluno.length; j++) {
                if (vetorAluno[j].disciplinasAluno[i].codigo == vetorDisciplinas[i].codigo) {
                    System.out.println("ALUNO: " + vetorAluno[j].nome);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static double calcularMedia(double[] notas) {
        double soma = 0;
        for (int j = 0; j < notas.length; j++) {
            soma += notas[j];
        }

        return soma / notas.length;
    }

    public static void ordenarVetor(Aluno[] vetorAlunoOrdenado) {
        for (int i = 0; i < vetorAlunoOrdenado.length - 1; i++) {
            for (int j = 1; j < vetorAlunoOrdenado.length; j++) {
                if (vetorAlunoOrdenado[i].media < vetorAlunoOrdenado[j].media) {
                    Aluno aux = vetorAlunoOrdenado[i];
                    vetorAlunoOrdenado[i] = vetorAlunoOrdenado[j];
                    vetorAlunoOrdenado[j] = aux;
                }
            }
        }

    }

    public static int buscarDisciplinaNoVetor(Disciplina[] vetorDisciplinas, int codigoDisciplina) {
        for (int i = 0; i < vetorDisciplinas.length; i++) {
            if (vetorDisciplinas[i].codigo == codigoDisciplina) {
                return i;
            }
        }
        return -1;
    }

    public static boolean verificarNomeDisciplinaDuplicado(Disciplina[] vetorDisciplinas, String nomeDisciplina) {
        boolean encontrado = false;
        for (int i = 0; i < vetorDisciplinas.length; i++) {
            if (vetorDisciplinas[i].nome != null) {
                if (vetorDisciplinas[i].nome.equalsIgnoreCase(nomeDisciplina)) {
                    encontrado = true;
                    break;
                }
            }
        }
        return encontrado;
    }

    public static boolean verificarCodDisciplinaDuplicado(Disciplina[] vetorDisciplinas, int codDisciplina) {
        boolean encontrado = false;
        for (int i = 0; i < vetorDisciplinas.length; i++) {
            if (vetorDisciplinas[i].codigo == codDisciplina) {
                encontrado = true;
                break;
            }
        }
        return encontrado;
    }

    public static boolean verificarNomeAlunoDuplicado(Aluno[] alunos, String nomeAluno) {
        boolean encontrado = false;
        for (int i = 0; i < alunos.length; i++) {
            if (alunos[i].nome != null) {
                if (alunos[i].nome.equalsIgnoreCase(nomeAluno)) {
                    encontrado = true;
                    break;
                }
            }
        }
        return encontrado;
    }

}
