import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

class Time {

	private String nome, apelido, estadio, tecnico, liga, nomeArquivo;
	private String capacidadePalavra, data;
	private int fundacaoDia, fundacaoMes, fundacaoAno, capacidade;
	private long paginaTam;

	//////////////////////////////////////////
	// Construtores da classe

	Time() { // Construtor default
		setNome("");
		setApelido("");
		setEstadio("");
		setTecnico("");
		setLiga("");
		setNomeArquivo("");
		setCapacidadePalavra("");
		setData("");
		setFundacaoDia(0);
		setFundacaoMes(0);
		setFundacaoAno(0);
		setCapacidade(0);
		setPaginaTam(0);
	}

	Time(String nome1, String apelido1, String estadio1, String tecnico1, String liga1, String nomeArquivo1,
			String capacidadePalavra1, String fundacaoData1, int fundacaoDia1, int fundacaoMes1, int fundacaoAno1,
			int capacidade1, long paginaTam1) { // Construtor por
		// parametro
		setNome(nome1);
		setApelido(apelido1);
		setEstadio(estadio1);
		setTecnico(tecnico1);
		setLiga(liga1);
		setNomeArquivo(nomeArquivo1);
		setCapacidadePalavra(capacidadePalavra1);
		setFundacaoAno(fundacaoAno1);
		setFundacaoDia(fundacaoDia1);
		setFundacaoMes(fundacaoMes1);
		setCapacidade(capacidade1);
		setData(fundacaoData1);
		setPaginaTam(paginaTam1);
	}
	/////////////////////////////////////////
	// Gets e sets para as variaveis da classe

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getEstadio() {
		return estadio;
	}

	public void setEstadio(String estadio) {
		this.estadio = estadio;
	}

	public String getTecnico() {
		return tecnico;
	}

	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}

	public String getLiga() {
		return liga;
	}

	public void setLiga(String liga) {
		this.liga = liga;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getCapacidadePalavra() {
		return capacidadePalavra;
	}

	public void setCapacidadePalavra(String capacidadePalavra) {
		this.capacidadePalavra = capacidadePalavra;
	}

	public long getPaginaTam() {
		return paginaTam;
	}

	public void setPaginaTam(long paginaTam) {
		this.paginaTam = paginaTam;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getFundacaoDia() {
		return fundacaoDia;
	}

	public void setFundacaoDia(int fundacaoDia) {
		this.fundacaoDia = fundacaoDia;
	}

	public int getFundacaoMes() {
		return fundacaoMes;
	}

	public void setFundacaoMes(int fundacaoMes) {
		this.fundacaoMes = fundacaoMes;
	}

	public int getFundacaoAno() {
		return fundacaoAno;
	}

	public void setFundacaoAno(int fundacaoAno) {
		this.fundacaoAno = fundacaoAno;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	// Fim gets e sets
	//////////////////////////////////////////

	public static Time Construir(String path) throws Exception {
		Time time = new Time();
		File file = new File(path);
		FileInputStream arq = new FileInputStream(file);
		BufferedReader lerArq = new BufferedReader(new InputStreamReader(arq, "UTF-8"));
		String linha1 = lerArq.readLine();
		String linha2 = "";
		int verifica = 0;

		while (lerArq.ready()) {
			linha1 = lerArq.readLine();
			if (linha1.contains("vcard")) {
				verifica = 1;
			}

			if (verifica >= 1 && verifica <= 12) {
				linha2 = linha2 + linha1;
				verifica++;
			}
		}

		linha1 = linha2;

		lerArq.close();
		///////////////////////////////////
		// Setando o nome do arquivo
		time.setNomeArquivo(path);
		//////////////////////////////////
		// Limpando a linha
		linha1 = linha1.replaceAll("<i>", " ");
		linha1 = linha1.replaceAll("</i>", "");
		linha1 = linha1.replaceAll("<br />", " ");
		linha1 = linha1.replaceAll("<td>", "");
		linha1 = linha1.replaceAll("</td>", "");
		linha1 = linha1.replaceAll("<tr>", "");
		linha1 = linha1.replaceAll("</tr>", "");
		linha1 = linha1.replaceAll("<a>", "/");
		linha1 = linha1.replaceAll("style=\"text-align:center\"", "");
		linha1 = linha1.replaceAll("style=\"white-space: nowrap; text-align:left\"", "");
		linha1 = linha1.replaceAll("scope=\"row\"", "");
		linha1 = linha1.replaceAll("</caption>", "");
		linha1 = linha1.replaceAll("<tbody>", "");
		linha1 = linha1.replaceAll("</span>", "");
		linha1 = linha1.replaceAll("<tr class=\"vcard agent\">", "");
		linha1 = linha1.replaceAll("<ul>", "");
		linha1 = linha1.replaceAll("<li>", "");

		/////////////////////////////////////
		// Limpando mais ainda a linha1
		StringBuilder linhaChar = new StringBuilder(linha1);
		for (int j = 0; j < linhaChar.length(); j++) {
			if (linhaChar.charAt(j) == '<' && linhaChar.charAt(j + 1) == 't' && linhaChar.charAt(j + 2) == 'a'
					&& linhaChar.charAt(j + 3) == 'b' && linhaChar.charAt(j + 4) == 'l'
					&& linhaChar.charAt(j + 5) == 'e') {// If para table
				while (linhaChar.charAt(j) != '>') {
					linhaChar.deleteCharAt(j);
				}
				// linhaChar.deleteCharAt(j);
			}
			if (linhaChar.charAt(j) == '<' && linhaChar.charAt(j + 1) == 'c' && linhaChar.charAt(j + 2) == 'a'
					&& linhaChar.charAt(j + 3) == 'p' && linhaChar.charAt(j + 4) == 't'
					&& linhaChar.charAt(j + 5) == 'i' && linhaChar.charAt(j + 6) == 'o'
					&& linhaChar.charAt(j + 7) == 'n') {// If para caption
				while (linhaChar.charAt(j) != '>') {
					linhaChar.deleteCharAt(j);
				}
				// linhaChar.deleteCharAt(j);
			}

			if (linhaChar.charAt(j) == '<' && linhaChar.charAt(j + 1) == 'a') {// If para <a
				while (linhaChar.charAt(j) != '>') {
					linhaChar.deleteCharAt(j);
				}
				// linhaChar.deleteCharAt(j);
			}
			if (linhaChar.charAt(j) == '<' && linhaChar.charAt(j + 1) == 't' && linhaChar.charAt(j + 2) == 'd') {// If
																													// para
																													// <td
				while (linhaChar.charAt(j) != '>') {
					linhaChar.deleteCharAt(j);
				}
			}
			if (linhaChar.charAt(j) == '<' && linhaChar.charAt(j + 1) == 'i' && linhaChar.charAt(j + 2) == 'm'
					&& linhaChar.charAt(j + 3) == 'g') {// If para <img
				while (linhaChar.charAt(j) != '>') {
					linhaChar.deleteCharAt(j);
				}
				// linhaChar.deleteCharAt(j);
			}
			if (linhaChar.charAt(j) == '<' && linhaChar.charAt(j + 1) == 's' && linhaChar.charAt(j + 2) == 'p'
					&& linhaChar.charAt(j + 3) == 'a' && linhaChar.charAt(j + 4) == 'n') {// If para span
				while (linhaChar.charAt(j) != '>') {
					linhaChar.deleteCharAt(j);
				}
			}

			if (linhaChar.charAt(j) == '<' && linhaChar.charAt(j + 1) == 's' && linhaChar.charAt(j + 2) == 'u'
					&& linhaChar.charAt(j + 3) == 'p') {// If para <sup
				while (linhaChar.charAt(j) != '>') {
					linhaChar.deleteCharAt(j);
				}
			}

			if (linhaChar.charAt(j) == '<' && linhaChar.charAt(j + 1) == 'i') {// If para <i
				while (linhaChar.charAt(j) != '>') {
					linhaChar.deleteCharAt(j);
				}
			}

			if (linhaChar.charAt(j) == '<' && linhaChar.charAt(j + 1) == 'd' && linhaChar.charAt(j + 2) == 'i'
					&& linhaChar.charAt(j + 3) == 'v') {// If para <div
				while (linhaChar.charAt(j) != '>') {
					linhaChar.deleteCharAt(j);
				}
			}
		}
		String linha = linhaChar.toString();
		linha = linha.replaceAll(">>", ">");
		linha = linha.replaceAll(">>>", ">");
		linha = linha.replaceAll(">>>>", ">");
		linha = linha.replaceAll("> <", "<");
		linha = linha.replaceAll("<th  >", "<th>");
		linha = linha.replaceAll(" <th>", "<th>");
		linha = linha.replaceAll("</th> ", "</th>");
		linha = linha.replaceAll("</th>>", "</th>");
		linha = linha.replaceAll("</a> >", "/");

		//////////////////////////
		// Pegadando os dados do html
		/////////////////////////
		// Pegando o nome do time
		String nome = "";
		if (linha.contains(">Full name</th>")) {
			nome = linha.substring(linha.indexOf(">Full name</th>") + 15);
			nome = nome.replaceAll("&#160;", "");
			nome = nome.replaceAll(" <small>", "");
			nome = nome.replaceAll("</a>", "");
			nome = nome.replaceAll("<b>", "");
			nome = nome.replaceAll("</b>", "");
			nome = nome.replaceAll("&amp;", "");
			nome = nome.substring(0, nome.indexOf("<"));
			nome = nome.replaceAll("\\( ", "(");
			nome = nome.replaceAll(" >", " ");
		} else if (linha.contains(">full name</th>")) {
			nome = linha.substring(linha.indexOf(">full name</th>") + 15);
			nome = nome.replaceAll("&#160;", "");
			nome = nome.replaceAll(" <small>", "");
			nome = nome.replaceAll("</a>", "");
			nome = nome.replaceAll("<b>", "");
			nome = nome.replaceAll("</b>", "");
			nome = nome.replaceAll("&amp;", "");
			nome = nome.substring(0, nome.indexOf("<"));
			nome = nome.replaceAll("\\( ", "(");
			nome = nome.replaceAll(" >", " ");
		}
		if (nome.contains(">")) {
			nome = nome.substring(0, nome.indexOf(">"));
		}
		time.setNome(nome.trim());
		///////////////////////////////
		// Pegando o apelido do time
		String apelido = "";
		if (linha.contains(">Nickname(s)</th>")) {
			apelido = linha.substring(linha.indexOf(">Nickname(s)</th>") + 17);
			apelido = apelido.replaceAll("&amp;", "");
			apelido = apelido.replaceAll(">&#91;1&#93;</a></sup>  ", "");
			apelido = apelido.replaceAll("</li>", "");
			apelido = apelido.replaceAll(" >", " ");
			apelido = apelido.replaceAll("</a>\\)  ", ")");
			apelido = apelido.replaceAll("</a>", "");
			apelido = apelido.replaceAll("\\(>", "(");
			apelido = apelido.replaceAll("</a>", "");
			apelido = apelido.substring(0, apelido.indexOf("<"));
			apelido = apelido.replaceAll("  ", " ");
			apelido = apelido.replaceAll("\\( ", "(");
			apelido = apelido.replaceAll("\\) ", ")");
			apelido = apelido.replaceAll("\"", "");
		} else if (linha.contains(">nickname(s)</th>")) {
			apelido = linha.substring(linha.indexOf(">nickname(s)</th>") + 17);
			apelido = apelido.replaceAll("&amp;", "");
			apelido = apelido.replaceAll(">&#91;1&#93;</a></sup>  ", "");
			apelido = apelido.replaceAll("</li>", "");
			apelido = apelido.replaceAll(" >", " ");
			apelido = apelido.replaceAll("</a>\\)  ", ")");
			apelido = apelido.replaceAll("</a>", "");
			apelido = apelido.replaceAll("\\(>", "(");
			apelido = apelido.replaceAll("</a>", "");
			apelido = apelido.substring(0, apelido.indexOf("<"));
			apelido = apelido.replaceAll("  ", " ");
			apelido = apelido.replaceAll("\\( ", "(");
			apelido = apelido.replaceAll("\\) ", ")");
			apelido = apelido.replaceAll("\"", "");
		}
		if (apelido.contains(">")) {
			apelido = apelido.substring(0, apelido.indexOf(">"));
		}
		time.setApelido(apelido.trim());
		//////////////////////////////
		// Dando replace nos meses em ingles para seu correspondente em numero

		linha = linha.replaceAll("January", "10000");
		linha = linha.replaceAll("February", "20000");
		linha = linha.replaceAll("March", "30000");
		linha = linha.replaceAll("April", "40000");
		linha = linha.replaceAll("May", "50000");
		linha = linha.replaceAll("June", "60000");
		linha = linha.replaceAll("July", "70000");
		linha = linha.replaceAll("August", "80000");
		linha = linha.replaceAll("September", "90000");
		linha = linha.replaceAll("October", "100000");
		linha = linha.replaceAll("November", "110000");
		linha = linha.replaceAll("December", "120000");
		/////////////////////////////
		// Pegando a fundacaoDia do time
		String data = "";
		if (linha.contains(">Founded</th>")) {
			data = linha.substring(linha.indexOf(">Founded</th>") + 13);
			data = data.replaceAll("Turin</a>, >Italy</a> \\(", "");
			data = data.substring(0, data.indexOf("<"));
			data = data.replaceAll("&#160;", " ");
			data = data.replaceAll("th,", "");
			if (data.contains(">")) {
				data = data.substring(0, data.indexOf(">"));
			}
			data = data.replaceAll(",", "");
			String fundacao[] = data.split(" ");
			int valorAux = Integer.parseInt(fundacao[0]);
			if (valorAux > 40 && valorAux <= 2019) {
				time.setFundacaoAno(valorAux);
				time.setFundacaoMes(0);
				time.setFundacaoDia(0);
			} else if (fundacao.length == 3) {
				if (Integer.parseInt(fundacao[0]) < 40) {
					time.setFundacaoDia(Integer.parseInt(fundacao[0]));
				}
				if (Integer.parseInt(fundacao[1]) < 40) {
					time.setFundacaoDia(Integer.parseInt(fundacao[1]));
				}
				if (Integer.parseInt(fundacao[0]) >= 10000) {
					time.setFundacaoMes(Integer.parseInt(fundacao[0]) / 10000);
				}
				if (Integer.parseInt(fundacao[1]) >= 10000) {
					time.setFundacaoMes(Integer.parseInt(fundacao[1]) / 10000);
				}
				time.setFundacaoAno(Integer.parseInt(fundacao[2]));
			} else if (fundacao.length == 2) {
				time.setFundacaoDia(0);
				time.setFundacaoMes(Integer.parseInt(fundacao[0]) / 10000);
				time.setFundacaoAno(Integer.parseInt(fundacao[1]));
			}
		} else if (linha.contains(">founded</th>")) {
			data = linha.substring(linha.indexOf(">founded</th>") + 13);
			data = data.replaceAll("Turin</a>, >Italy</a> \\(", "");
			data = data.substring(0, data.indexOf("<"));
			data = data.replaceAll("&#160;", " ");
			data = data.replaceAll("th,", "");
			if (data.contains(">")) {
				data = data.substring(0, data.indexOf(">"));
			}
			data = data.replaceAll(",", "");
			String fundacao[] = data.split(" ");
			int valorAux = Integer.parseInt(fundacao[0]);
			if (valorAux > 40 && valorAux <= 2019) {
				time.setFundacaoAno(valorAux);
				time.setFundacaoMes(0);
				time.setFundacaoDia(0);
			} else if (fundacao.length == 3) {
				if (Integer.parseInt(fundacao[0]) < 40) {
					time.setFundacaoDia(Integer.parseInt(fundacao[0]));
				}
				if (Integer.parseInt(fundacao[1]) < 40) {
					time.setFundacaoDia(Integer.parseInt(fundacao[1]));
				}
				if (Integer.parseInt(fundacao[0]) >= 10000) {
					time.setFundacaoMes(Integer.parseInt(fundacao[0]) / 10000);
				}
				if (Integer.parseInt(fundacao[1]) >= 10000) {
					time.setFundacaoMes(Integer.parseInt(fundacao[1]) / 10000);
				}
				time.setFundacaoAno(Integer.parseInt(fundacao[2]));
			} else if (fundacao.length == 2) {
				time.setFundacaoDia(0);
				time.setFundacaoMes(Integer.parseInt(fundacao[0]) / 10000);
				time.setFundacaoAno(Integer.parseInt(fundacao[1]));
			}
		}
		time.setData(data);
		///////////////////////////////
		// Pegando o estadio
		String estadio = "";
		if (linha.contains(">Ground</th>")) {
			estadio = linha.substring(linha.indexOf(">Ground</th>") + 12);
			estadio = estadio.replaceAll("</a>, >", ", ");
			estadio = estadio.replaceAll("</a>", "");
			estadio = estadio.replaceAll("/", "");
			estadio = estadio.replaceAll(" >", "");
			estadio = estadio.substring(0, estadio.indexOf("<"));
		} else if (linha.contains(">ground</th>")) {
			estadio = linha.substring(linha.indexOf(">ground</th>") + 12);
			estadio = estadio.replaceAll("</a>, >", ", ");
			estadio = estadio.replaceAll("</a>", "");
			estadio = estadio.replaceAll("/", "");
			estadio = estadio.replaceAll(" >", "");
			estadio = estadio.substring(0, estadio.indexOf("<"));
		}
		time.setEstadio(estadio.trim());
		///////////////////////////////
		// Pegando a capacidade do estadio
		String estadioCap = "";
		if (linha.contains(">Capacity</th>")) {
			estadioCap = linha.substring(linha.indexOf(">Capacity</th>") + 14);
			estadioCap = estadioCap.replace(",", "");
			estadioCap = estadioCap.replace(" ", "");
			estadioCap = estadioCap.replace(".", "");
			estadioCap = estadioCap.replaceAll("or", "/");
			estadioCap = estadioCap.substring(0, estadioCap.indexOf(">"));
			estadioCap = estadioCap.replaceAll("<th", "");
			if (estadioCap.contains("(")) {
				estadioCap = estadioCap.substring(0, estadioCap.indexOf("("));
			}
			if (estadioCap.contains("/")) {
				estadioCap = estadioCap.substring(0, estadioCap.indexOf("/"));
			}
			if (estadioCap.contains(";")) {
				estadioCap = estadioCap.substring(0, estadioCap.indexOf(";"));
			}
		} else if (linha.contains(">capacity</th>")) {
			estadioCap = linha.substring(linha.indexOf(">capacity</th>") + 14);
			estadioCap = estadioCap.replace(",", "");
			estadioCap = estadioCap.replace(" ", "");
			estadioCap = estadioCap.replace(".", "");
			estadioCap = estadioCap.replaceAll("or", "/");
			estadioCap = estadioCap.substring(0, estadioCap.indexOf(">"));
			estadioCap = estadioCap.replaceAll("<th", "");
			if (estadioCap.contains("(")) {
				estadioCap = estadioCap.substring(0, estadioCap.indexOf("("));
			}
			if (estadioCap.contains("/")) {
				estadioCap = estadioCap.substring(0, estadioCap.indexOf("/"));
			}
			if (estadioCap.contains(";")) {
				estadioCap = estadioCap.substring(0, estadioCap.indexOf(";"));
			}
		}
		time.setCapacidadePalavra(estadioCap);
		time.setCapacidade(Integer.parseInt(estadioCap));
		///////////////////////////////
		// Pegando o tecnico do time
		String tecnico = "";
		if (linha.contains(">Manager</th>")) {
			tecnico = linha.substring(linha.indexOf(">Manager</th>") + 13);
			tecnico = tecnico.replaceAll("</a>", "");
			tecnico = tecnico.replaceAll(" >", " ");
			tecnico = tecnico.substring(0, tecnico.indexOf("<"));
			if (tecnico.contains(">")) {
				tecnico = tecnico.substring(0, tecnico.indexOf(">"));
			}
		} else if (linha.contains(">manager</th>")) {
			tecnico = linha.substring(linha.indexOf(">manager</th>") + 13);
			tecnico = tecnico.replaceAll("</a>", "");
			tecnico = tecnico.replaceAll(" >", " ");
			tecnico = tecnico.substring(0, tecnico.indexOf("<"));
			if (tecnico.contains(">")) {
				tecnico = tecnico.substring(0, tecnico.indexOf(">"));
			}
		} else if (linha.contains(">Head Coach</th>")) {
			tecnico = linha.substring(linha.indexOf(">Head Coach</th>") + 16);
			tecnico = tecnico.replaceAll("</a>", "");
			tecnico = tecnico.replaceAll(" >", " ");
			tecnico = tecnico.substring(0, tecnico.indexOf("<"));
			if (tecnico.contains(">")) {
				tecnico = tecnico.substring(0, tecnico.indexOf(">"));
			}
		} else if (linha.contains(">Head coach</th>")) {
			tecnico = linha.substring(linha.indexOf(">Head coach</th>") + 16);
			tecnico = tecnico.replaceAll("</a>", "");
			tecnico = tecnico.replaceAll(" >", " ");
			tecnico = tecnico.substring(0, tecnico.indexOf("<"));
			if (tecnico.contains(">")) {
				tecnico = tecnico.substring(0, tecnico.indexOf(">"));
			}
		} else if (linha.contains(">head coach</th>")) {
			tecnico = linha.substring(linha.indexOf(">head coach</th>") + 16);
			tecnico = tecnico.replaceAll("</a>", "");
			tecnico = tecnico.replaceAll(" >", " ");
			tecnico = tecnico.substring(0, tecnico.indexOf("<"));
			if (tecnico.contains(">")) {
				tecnico = tecnico.substring(0, tecnico.indexOf(">"));
			}
		} else if (linha.contains(">Coach")) {
			tecnico = linha.substring(linha.indexOf(">Coach</th>") + 11);
			tecnico = tecnico.replaceAll("</a>", "");
			tecnico = tecnico.replaceAll(" >", " ");
			tecnico = tecnico.substring(0, tecnico.indexOf("<"));
			if (tecnico.contains(">")) {
				tecnico = tecnico.substring(0, tecnico.indexOf(">"));
			}
		} else if (linha.contains(">coach")) {
			tecnico = linha.substring(linha.indexOf(">coach</th>") + 11);
			tecnico = tecnico.replaceAll("</a>", "");
			tecnico = tecnico.replaceAll(" >", " ");
			tecnico = tecnico.substring(0, tecnico.indexOf("<"));
			if (tecnico.contains(">")) {
				tecnico = tecnico.substring(0, tecnico.indexOf(">"));
			}
		}
		time.setTecnico(tecnico.trim());
		/////////////////////////////
		// Pegando a liga
		String liga = "";
		if (linha.contains(">League</th>")) {
			liga = linha.substring(linha.indexOf(">League</th>") + 12);
			liga = liga.replaceAll("</a>", "");
			liga = liga.substring(0, liga.indexOf("<"));
		} else if (linha.contains(">league</th>")) {
			liga = linha.substring(linha.indexOf(">league</th>") + 12);
			liga = liga.replaceAll("</a>", "");
			liga = liga.substring(0, liga.indexOf("<"));
		}
		time.setLiga(liga.trim());
		///////////////////////////////////
		// Contando a quantidade de letras no html
		Arq.openRead(path, "UTF-8");
		long tamanhoHtml = Arq.length();
		time.setPaginaTam(tamanhoHtml);
		//////////////////////////////////
		return time;

	}// FIM metodo para construir um time atraves do path do arquivo

	public void imprimir() {
		if (!(this.fundacaoMes < 10) && (this.fundacaoDia > 9)) {
			MyIO.println(this.nome + " ## " + this.apelido + " ## " + this.fundacaoDia + "/" + this.fundacaoMes + "/"
					+ this.getFundacaoAno() + " ## " + this.estadio + " ## " + this.capacidade + " ## " + this.tecnico
					+ " ## " + this.liga + " ## " + this.nomeArquivo + " ## " + this.paginaTam + " ## ");
		} else if ((this.fundacaoMes < 10) && (this.fundacaoDia < 10)) {
			MyIO.println(this.nome + " ## " + this.apelido + " ## " + "0" + this.fundacaoDia + "/0" + this.fundacaoMes
					+ "/" + this.getFundacaoAno() + " ## " + this.estadio + " ## " + this.capacidade + " ## "
					+ this.tecnico + " ## " + this.liga + " ## " + this.nomeArquivo + " ## " + this.paginaTam + " ## ");
		} else if (!(this.fundacaoMes < 10) && (this.fundacaoDia < 10)) {
			MyIO.println(this.nome + " ## " + this.apelido + " ## " + "0" + this.fundacaoDia + "/" + this.fundacaoMes
					+ "/" + this.getFundacaoAno() + " ## " + this.estadio + " ## " + this.capacidade + " ## "
					+ this.tecnico + " ## " + this.liga + " ## " + this.nomeArquivo + " ## " + this.paginaTam + " ## ");
		} else {
			MyIO.println(this.nome + " ## " + this.apelido + " ## " + this.fundacaoDia + "/0" + this.fundacaoMes + "/"
					+ this.getFundacaoAno() + " ## " + this.estadio + " ## " + this.capacidade + " ## " + this.tecnico
					+ " ## " + this.liga + " ## " + this.nomeArquivo + " ## " + this.paginaTam + " ## ");
		}

	}// fim do metodo imprimir

}// Fim class time

public class Futebol_635921 {
	public static void main(String[] arg) throws Exception {
		MyIO.setCharset("UTF-8");
		String path = null;
		int contador = 0;

		Time[] times = new Time[1000];

		path = MyIO.readLine(); // Recebimento da entrada
		// try {
		while (!path.equals("FIM")) {
			if (path == "" || path == null) // Caso entrada for nula ou vazia
			{
				MyIO.println("Entrada Vazia!");
			} // Fim if
			else {
				times[contador] = Time.Construir(path);
				contador++;
			}
			path = MyIO.readLine(); // Recebimento da entrada
		}
		// } catch (Exception e) {
		// }
		for (int i = 0; i < contador; i++) {
			times[i].imprimir();
		}

	}// Fim main
}
