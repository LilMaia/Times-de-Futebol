#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Time {
	char nome[18950], apelido[18950], estadio[18950], tecnico[18950],
			liga[18950], nomeArquivo[18950];
	char capacidadePalavra[18950], data[18950];
	int fundacaoDia, fundacaoMes, fundacaoAno, capacidade;
	long paginaTam;
} Time;

FILE* abrirArquivo(const char nomeArquivo[18950]);
char *replace_str(char *orig, char *rep, char *with);
char* removeTags(char* word);
char* pegarCoach(char* str);
char* pegarHead(char* str);
char* pegarManager(char* str);
char* pegarTecnico(char* str);
char* pegarCapacidade(char* str);
char* pegarLiga(char* str);
char* pegarEstadio(char* str);
char* pegarData(char* str);
char* pegarApelido(char* str);
char* pegarNome(char* str);
Time setarInformacoes(char path[18950]);
void setPadrao(Time *time);
void imprime(Time time);
char* imprimeS(Time time);

FILE* abrirArquivo(const char nomeArquivo[18950]) {
	FILE *fp = fopen(nomeArquivo, "r");
	if (fp == NULL) {
		printf("Falha na abertura do arquivo %s\n", nomeArquivo);
	}
	return fp;
}

char *replace_str(char *orig, char *rep, char *with) {
	char *result; // the return string
	char *ins;    // the next insert point
	char *tmp;    // varies
	int len_rep;  // length of rep (the string to remove)
	int len_with; // length of with (the string to replace rep with)
	int len_front; // distance between rep and end of last rep
	int count;    // number of replacements

	// sanity checks and initialization
	if (!orig || !rep)
		return NULL;
	len_rep = strlen(rep);
	if (len_rep == 0)
		return NULL; // empty rep causes infinite loop during count
	if (!with)
		with = "";
	len_with = strlen(with);

	// count the number of replacements needed
	ins = orig;
	for (count = 0; tmp = strstr(ins, rep); ++count) {
		ins = tmp + len_rep;
	}

	tmp = result = (char*)malloc(strlen(orig) + (len_with - len_rep) * count + 1);

	if (!result)
		return NULL;

	// first time through the loop, all the variable are set correctly
	// from here on,
	//    tmp points to the end of the result string
	//    ins points to the next occurrence of rep in orig
	//    orig points to the remainder of orig after "end of rep"
	while (count--) {
		ins = strstr(orig, rep);
		len_front = ins - orig;
		tmp = strncpy(tmp, orig, len_front) + len_front;
		tmp = strcpy(tmp, with) + len_with;
		orig += len_front + len_rep; // move to next "end of rep"
	}
	strcpy(tmp, orig);
	return result;
}

char* removeTags(char* word) {

	word = replace_str(word, "<i>", " ");
	word = replace_str(word, "</i>", "");
	word = replace_str(word, "<br />", " ");
	word = replace_str(word, "<td>", "");
	word = replace_str(word, "</td>", "");
	word = replace_str(word, "<tr>", "");
	word = replace_str(word, "</tr>", "");
	word = replace_str(word, "<a>", "/");
	word = replace_str(word, "style=\"text-align:center\"", "");
	word = replace_str(word, "style=\"white-space: nowrap; text-align:left\"",
			"");
	word = replace_str(word, "scope=\"row\"", "");
	word = replace_str(word, "</caption>", "");
	word = replace_str(word, "<tbody>", "");
	word = replace_str(word, "</span>", "");
	word = replace_str(word, "<tr class=\"vcard agent\">", "");
	word = replace_str(word, "<ul>", "");
	word = replace_str(word, "<li>", "");

	for (int j = 0; j < strlen(word); j++) {
		if (word[j] == '<' && word[j + 1] == 't' && word[j + 2] == 'a'
				&& word[j + 3] == 'b' && word[j + 4] == 'l'
				&& word[j + 5] == 'e') { // If para table
			while (word[j] != '>') {
				word[j] = '!';
				j++;
			}
			// word.deleteCharAt(j);
		}
		if (word[j] == '<' && word[j + 1] == 'c' && word[j + 2] == 'a'
				&& word[j + 3] == 'p' && word[j + 4] == 't'
				&& word[j + 5] == 'i' && word[j + 6] == 'o'
				&& word[j + 7] == 'n') { // If para caption
			while (word[j] != '>') {
				word[j] = '!';
				j++;
			}
			// word.deleteCharAt(j);
		}

		if (word[j] == '<' && word[j + 1] == 'a') { // If para <a
			while (word[j] != '>') {
				word[j] = '!';
				j++;
			}
			// word.deleteCharAt(j);
		}
		if (word[j] == '<' && word[j + 1] == 't' && word[j + 2] == 'd') { // If
																		  // para
																		  // <td
			while (word[j] != '>') {
				word[j] = '!';
				j++;
			}
		}
		if (word[j] == '<' && word[j + 1] == 'i' && word[j + 2] == 'm'
				&& word[j + 3] == 'g') {						// If para <img
			while (word[j] != '>') {
				word[j] = '!';
				j++;
			}
			// word.deleteCharAt(j);
		}
		if (word[j] == '<' && word[j + 1] == 's' && word[j + 2] == 'p'
				&& word[j + 3] == 'a' && word[j + 4] == 'n') {	// If para span
			while (word[j] != '>') {
				word[j] = '!';
				j++;
			}
		}

		if (word[j] == '<' && word[j + 1] == 's' && word[j + 2] == 'u'
				&& word[j + 3] == 'p') {						// If para <sup
			while (word[j] != '>') {
				word[j] = '!';
				j++;
			}
		}

		if (word[j] == '<' && word[j + 1] == 'i') {				// If para <i
			while (word[j] != '>') {
				word[j] = '!';
				j++;
			}
		}

		if (word[j] == '<' && word[j + 1] == 'd' && word[j + 2] == 'i'
				&& word[j + 3] == 'v') {						// If para <div
			while (word[j] != '>') {
				word[j] = '!';
				j++;
			}
		}
	}

	word = replace_str(word, ">>", ">");
	word = replace_str(word, ">>>", ">");
	word = replace_str(word, ">>>>", ">");
	word = replace_str(word, "> <", "<");
	word = replace_str(word, "<th  >", "<th>");
	word = replace_str(word, " <th>", "<th>");
	word = replace_str(word, "</th> ", "</th>");
	word = replace_str(word, "</th>>", "</th>");
	word = replace_str(word, "</a> >", "/");

	return word;
}

char* pegarCoach(char* str) {
	char *pch = (char*) malloc(18950 * sizeof(char));
	char *aux = (char*) malloc(18950 * sizeof(char));

	pch = strstr(str, "oach</th>");
	int i = 0;
	while (pch[i + 9] != '<') {
		aux[i] = pch[i + 9];
		i++;
	}
	aux[i] = '\0';

	aux = replace_str(aux, "!", "");
	aux = replace_str(aux, "> ", "");

	aux = replace_str(aux, "</a>", "");
	aux = replace_str(aux, " >", " ");

	aux = replace_str(aux, ">>", "");
	aux = replace_str(aux, ">", "");

	if (strstr(aux, "<") != NULL) {
		i = 0;
		while (aux[i] != '<') {
			i++;
		}
		aux[i] = '\0';
	}

	if (strstr(aux, ">") != NULL) {
		i = 0;
		while (aux[i] != '>') {
			i++;
		}
		aux[i] = '\0';
	}

	return aux;

}

char* pegarHead(char* str) {
	char *pch = (char*) malloc(18950 * sizeof(char));
	char *aux = (char*) malloc(18950 * sizeof(char));

	if (strstr(str, "ead Coach</th>")) {

		pch = strstr(str, "ead Coach</th>");
		int i = 0;
		while (pch[i + 14] != '<') {
			aux[i] = pch[i + 14];
			i++;
		}
		aux[i] = '\0';

		aux = replace_str(aux, "!", "");
		aux = replace_str(aux, "> ", "");

		aux = replace_str(aux, "</a>", "");
		aux = replace_str(aux, " >", " ");

		aux = replace_str(aux, ">>", "");
		aux = replace_str(aux, ">", "");

		if (strstr(aux, "<") != NULL) {
			i = 0;
			while (aux[i] != '<') {
				i++;
			}
			aux[i] = '\0';
		}

		if (strstr(aux, ">") != NULL) {
			i = 0;
			while (aux[i] != '>') {
				i++;
			}
			aux[i] = '\0';
		}

	} else if (strstr(str, "ead coach</th>")) {

		pch = strstr(str, "ead coach</th>");
		int i = 0;
		while (pch[i + 14] != '<') {
			aux[i] = pch[i + 14];
			i++;
		}
		aux[i] = '\0';

		aux = replace_str(aux, "!", "");
		aux = replace_str(aux, "> ", "");

		aux = replace_str(aux, "</a>", "");
		aux = replace_str(aux, " >", " ");

		aux = replace_str(aux, ">>", "");
		aux = replace_str(aux, ">", "");

		if (strstr(aux, "<") != NULL) {
			i = 0;
			while (aux[i] != '<') {
				i++;
			}
			aux[i] = '\0';
		}

		if (strstr(aux, ">") != NULL) {
			i = 0;
			while (aux[i] != '>') {
				i++;
			}
			aux[i] = '\0';
		}

	}

	return aux;

}

char* pegarManager(char* str) {
	char *pch = (char*) malloc(18950 * sizeof(char));
	char *aux = (char*) malloc(18950 * sizeof(char));

	pch = strstr(str, "anager</th>");
	int i = 0;
	while (pch[i + 11] != '<') {
		aux[i] = pch[i + 11];
		i++;
	}
	aux[i] = '\0';

	aux = replace_str(aux, "!", "");
	aux = replace_str(aux, "> ", "");

	aux = replace_str(aux, "</a>", "");
	aux = replace_str(aux, " >", " ");

	aux = replace_str(aux, ">>", "");
	aux = replace_str(aux, ">", "");

	if (strstr(aux, "<") != NULL) {
		i = 0;
		while (aux[i] != '<') {
			i++;
		}
		aux[i] = '\0';
	}

	if (strstr(aux, ">") != NULL) {
		i = 0;
		while (aux[i] != '>') {
			i++;
		}
		aux[i] = '\0';
	}

	return aux;

}

char* pegarTecnico(char* str) {
	char *aux = (char*) malloc(18950 * sizeof(char));

	if (strstr(str, "Manager")) {
		aux = pegarManager(str);
	} else if (strstr(str, "Head")) {
		aux = pegarHead(str);
	} else if (strstr(str, "Coach")) {
		aux = pegarCoach(str);
	}

	return aux;

}

char* pegarCapacidade(char* str) {
	char *pch = (char*) malloc(18950 * sizeof(char));
	char *aux = (char*) malloc(18950 * sizeof(char));

	pch = strstr(str, "apacity</th>");
	int i = 0;

	while (pch[i + 12] != '<') {
		aux[i] = pch[i + 12];
		i++;
	}

	aux[i] = '\0';

	aux = replace_str(aux, "!", "");
	aux = replace_str(aux, "> ", "");

	aux = replace_str(aux, ",", "");
	aux = replace_str(aux, " ", "");
	aux = replace_str(aux, ".", "");
	aux = replace_str(aux, "or", "/");

	if (strstr(aux, ">") != NULL) {
		i = 0;
		while (aux[i] != '>') {
			i++;
		}
		aux[i] = '\0';
	}

	aux = replace_str(aux, "<th", "");

	if (strstr(aux, "(") != NULL) {
		i = 0;
		while (aux[i] != '(') {
			i++;
		}
		aux[i] = '\0';
	}
	if (strstr(aux, "/") != NULL) {
		i = 0;
		while (aux[i] != '/') {
			i++;
		}
		aux[i] = '\0';
	}
	if (strstr(aux, ";") != NULL) {
		i = 0;
		while (aux[i] != ';') {
			i++;
		}
		aux[i] = '\0';
	}

	return aux;

}

char* pegarLiga(char* str) {
	char *pch = (char*) malloc(18950 * sizeof(char));
	char *aux = (char*) malloc(18950 * sizeof(char));

	pch = strstr(str, "eague</th>");
	int i = 0;
	while (pch[i + 10] != '<') {
		aux[i] = pch[i + 10];
		i++;
	}
	aux[i] = '\0';

	aux = replace_str(aux, "!", "");
	aux = replace_str(aux, "> ", "");

	aux = replace_str(aux, "</a>", "");
	aux = replace_str(aux, ">", "");

	return aux;

}

char* pegarEstadio(char* str) {
	char *pch = (char*) malloc(18950 * sizeof(char));
	char *aux = (char*) malloc(18950 * sizeof(char));

	pch = strstr(str, "round</th>");
	int i = 0;

	while (pch[i + 10] != '<') {
		aux[i] = pch[i + 10];
		i++;
	}

	aux[i] = '\0';

	aux = replace_str(aux, "!", "");
	aux = replace_str(aux, "> ", "");

	aux = replace_str(aux, "</a>, >", ", ");
	aux = replace_str(aux, "</a>", "");
	aux = replace_str(aux, "/", "");
	aux = replace_str(aux, " >", "");
	aux = replace_str(aux, ">>", "");

	return aux;

}

char* pegarData(char* str) {
	char *pch = (char*) malloc(18950 * sizeof(char));
	char *aux = (char*) malloc(18950 * sizeof(char));

	pch = strstr(str, "ounded</th>");
	int i = 0;
	while (pch[i + 11] != '<') {
		aux[i] = pch[i + 11];
		i++;
	}
	aux[i] = '\0';

	aux = replace_str(aux, "January", "10000");
	aux = replace_str(aux, "February", "20000");
	aux = replace_str(aux, "March", "30000");
	aux = replace_str(aux, "April", "40000");
	aux = replace_str(aux, "May", "50000");
	aux = replace_str(aux, "June", "60000");
	aux = replace_str(aux, "July", "70000");
	aux = replace_str(aux, "August", "80000");
	aux = replace_str(aux, "September", "90000");
	aux = replace_str(aux, "October", "100000");
	aux = replace_str(aux, "November", "110000");
	aux = replace_str(aux, "December", "120000");

	aux = replace_str(aux, "!", "");
	aux = replace_str(aux, "> ", "");

	aux = replace_str(aux, "Turin</a>, >Italy</a> \\(", "");
	aux = replace_str(aux, "&#160;", " ");
	aux = replace_str(aux, "th,", "");
	aux = replace_str(aux, ",", "");

	if (strstr(aux, ">") != NULL) {
		i = 0;
		while (aux[i] != '>') {
			i++;
		}
		aux[i] = '\0';
	}

	return aux;

}

char* pegarApelido(char* str) {
	char *pch = (char*) malloc(18950 * sizeof(char));
	char *aux = (char*) malloc(18950 * sizeof(char));
	pch = strstr(str, "ickname(s)");
	int i = 0;
	while (pch[i + 15] != '<') {
		aux[i] = pch[i + 15];
		i++;
	}
	aux[i] = '\0';
	aux = replace_str(aux, "!", "");
	aux = replace_str(aux, "> ", "");

	aux = replace_str(aux, "&amp;", "");
	aux = replace_str(aux, ">&#91;1&#93;</a></sup>  ", "");
	aux = replace_str(aux, "</li>", "");
	aux = replace_str(aux, " >", " ");
	aux = replace_str(aux, "</a>\\)  ", ")");
	aux = replace_str(aux, "</a>", "");
	aux = replace_str(aux, "\\(>", "(");
	aux = replace_str(aux, "</a>", "");
	aux = replace_str(aux, "  ", " ");
	aux = replace_str(aux, "\\( ", "(");
	aux = replace_str(aux, "\\) ", ")");
	aux = replace_str(aux, "\"", "");
	aux = replace_str(aux, ">", "");

	return aux;

}

char* pegarNome(char* str) {
	char *pch = (char*) malloc(18950 * sizeof(char));
	char *aux = (char*) malloc(18950 * sizeof(char));
	pch = strstr(str, "ull name");
	int i = 0;
	while (pch[i + 13] != '<') {
		aux[i] = pch[i + 13];
		i++;
	}
	aux[i] = '\0';
	aux = replace_str(aux, "!", "");
	aux = replace_str(aux, "> ", "");

	aux = replace_str(aux, "&#160;", "");
	aux = replace_str(aux, " <small>", "");
	aux = replace_str(aux, "</a>", "");
	aux = replace_str(aux, "<b>", "");
	aux = replace_str(aux, "</b>", "");
	aux = replace_str(aux, "&amp;", "");
	aux = replace_str(aux, "\\( ", "(");
	aux = replace_str(aux, " >", " ");

	return aux;

}										//fim do metodo que pega o nome do time

Time setarInformacoes(char path[18950]) {

	Time Time;
	//Abertura do Arquivo
	FILE *p = abrirArquivo(path);

	size_t len = 18950;
	int verifica = 0;
	char* linha = (char*) malloc(len * sizeof(char));
	char* texto = (char*) malloc(len * sizeof(char));

	//Pegar nome do arquivo
	strcpy(Time.nomeArquivo, path);

	//Leitura do codigo
	while (verifica != 12) {
		//Pegar parte essencial do Arquivo
		getline(&linha, &len, p);
		if (strstr(linha, "vcard") && verifica == 0) {
			strcpy(texto, linha);
			verifica++;
		} else if (verifica != 0) {
			strcat(texto, linha);
			verifica++;
		}
	}

	//printf("%s\n",texto);
	//printf("//////////////////////////////////////////////\n");
	//pegando o tamanho do arquivo
	fseek(p, 0, SEEK_END);
	int tamanhoArquivo = ftell(p);
	Time.paginaTam = tamanhoArquivo;

	//printf("%d",tamanhoArquivo);
	fclose(p);
	//Remover todas algumas tags do arquivo
	texto = removeTags(texto);

	//Pegar Nome
	char* nome = (char*) malloc(len * sizeof(char));
	nome = pegarNome(texto);
	strcpy(Time.nome, nome);

	//printf("%s\n", Time.nome);

	//Pegar apelido
	char* apelido = (char*) malloc(len * sizeof(char));
	apelido = pegarApelido(texto);
	strcpy(Time.apelido, apelido);

	//printf("%s\n", Time.apelido);

	//Pegar Fundaçao
	char* data = (char*) malloc(len * sizeof(char));
	data = pegarData(texto);
	strcpy(Time.data, data);

	char delim[] = " ";
	char *ptr = strtok(data, delim);

	if (atoi(ptr) > 40 && atoi(ptr) < 2019) {
		Time.fundacaoDia = 0;
		Time.fundacaoMes = 0;
		Time.fundacaoAno = atoi(ptr);
		ptr = NULL;
	} else if (atoi(ptr) >= 10000) {
		Time.fundacaoDia = 0;
		Time.fundacaoMes = atoi(ptr) / 10000;
		ptr = strtok(NULL, delim);
		Time.fundacaoAno = atoi(ptr);
		ptr = NULL;
	} else {
		Time.fundacaoDia = atoi(ptr);
		ptr = strtok(NULL, delim);
		Time.fundacaoMes = atoi(ptr) / 10000;
		ptr = strtok(NULL, delim);
		Time.fundacaoAno = atoi(ptr);
	}

	//printf("%d\n", Time.fundacaoDia);
	//printf("%d\n", Time.fundacaoMes);
	//printf("%d\n", Time.fundacaoAno);

	//Pegar Estadio
	char* estadio = (char*) malloc(len * sizeof(char));
	estadio = pegarEstadio(texto);
	strcpy(Time.estadio, estadio);

	//printf("%s\n", Time.estadio);

	//Pegar Liga
	char* liga = (char*) malloc(len * sizeof(char));
	liga = pegarLiga(texto);
	strcpy(Time.liga, liga);

	//printf("%s\n", Time.liga);

	//Pegar Capacidade
	char* capacidade = (char*) malloc(len * sizeof(char));
	capacidade = pegarCapacidade(texto);
	Time.capacidade = atoi(capacidade);

	//printf("%d\n", Time.capacidade);

	//Pegar Tecnico
	char* tecnico = (char*) malloc(len * sizeof(char));
	tecnico = pegarTecnico(texto);
	strcpy(Time.tecnico, tecnico);

	//printf("%s\n", Time.tecnico);
	//frees
	free(linha);
	free(texto);
	free(nome);
	free(apelido);
	free(estadio);
	free(liga);
	free(capacidade);
	free(tecnico);

	return Time;

} //Fim do metodo setarInformacoes

void setPadrao(Time *time) {
	strcpy(time->nome, "");
	strcpy(time->apelido, "");
	strcpy(time->estadio, "");
	strcpy(time->tecnico, "");
	strcpy(time->liga, "");
	strcpy(time->nomeArquivo, "");
	strcpy(time->capacidadePalavra, "");
	strcpy(time->data, "");
	time->fundacaoDia = 0;
	time->fundacaoMes = 0;
	time->fundacaoAno = 0;
	time->capacidade = 0;
	time->paginaTam = 0;
}

void imprime(Time time) {

	if (!(time.fundacaoMes < 10) && (time.fundacaoDia > 9)) {
		printf(
				"%s ## %s ## %d/%d/%d ## %s ## %d ## %s ## %s ## %s ## %ld ## \n",
				time.nome, time.apelido, time.fundacaoDia, time.fundacaoMes,
				time.fundacaoAno, time.estadio, time.capacidade, time.tecnico,
				time.liga, time.nomeArquivo, time.paginaTam);
	} else if ((time.fundacaoMes < 10) && (time.fundacaoDia < 10)) {
		printf(
				"%s ## %s ## 0%d/0%d/%d ## %s ## %d ## %s ## %s ## %s ## %ld ## \n",
				time.nome, time.apelido, time.fundacaoDia, time.fundacaoMes,
				time.fundacaoAno, time.estadio, time.capacidade, time.tecnico,
				time.liga, time.nomeArquivo, time.paginaTam);
	} else if (!(time.fundacaoMes < 10) && (time.fundacaoDia < 10)) {
		printf(
				"%s ## %s ## 0%d/%d/%d ## %s ## %d ## %s ## %s ## %s ## %ld ## \n",
				time.nome, time.apelido, time.fundacaoDia, time.fundacaoMes,
				time.fundacaoAno, time.estadio, time.capacidade, time.tecnico,
				time.liga, time.nomeArquivo, time.paginaTam);
	} else {
		printf(
				"%s ## %s ## %d/0%d/%d ## %s ## %d ## %s ## %s ## %s ## %ld ## \n",
				time.nome, time.apelido, time.fundacaoDia, time.fundacaoMes,
				time.fundacaoAno, time.estadio, time.capacidade, time.tecnico,
				time.liga, time.nomeArquivo, time.paginaTam);
	}
}

char* imprimeS(Time time) {
    char* str = (char*) malloc(18950 * sizeof(char));

	if (!(time.fundacaoMes < 10) && (time.fundacaoDia > 9)) {
		sprintf(str,
				"%s ## %s ## %d/%d/%d ## %s ## %d ## %s ## %s ## %s ## %ld ## \n",
				time.nome, time.apelido, time.fundacaoDia, time.fundacaoMes,
				time.fundacaoAno, time.estadio, time.capacidade, time.tecnico,
				time.liga, time.nomeArquivo, time.paginaTam);
	} else if ((time.fundacaoMes < 10) && (time.fundacaoDia < 10)) {
		sprintf(str,
				"%s ## %s ## 0%d/0%d/%d ## %s ## %d ## %s ## %s ## %s ## %ld ## \n",
				time.nome, time.apelido, time.fundacaoDia, time.fundacaoMes,
				time.fundacaoAno, time.estadio, time.capacidade, time.tecnico,
				time.liga, time.nomeArquivo, time.paginaTam);
	} else if (!(time.fundacaoMes < 10) && (time.fundacaoDia < 10)) {
		sprintf(str,
				"%s ## %s ## 0%d/%d/%d ## %s ## %d ## %s ## %s ## %s ## %ld ## \n",
				time.nome, time.apelido, time.fundacaoDia, time.fundacaoMes,
				time.fundacaoAno, time.estadio, time.capacidade, time.tecnico,
				time.liga, time.nomeArquivo, time.paginaTam);
	} else {
		sprintf(str,
				"%s ## %s ## %d/0%d/%d ## %s ## %d ## %s ## %s ## %s ## %ld ## \n",
				time.nome, time.apelido, time.fundacaoDia, time.fundacaoMes,
				time.fundacaoAno, time.estadio, time.capacidade, time.tecnico,
				time.liga, time.nomeArquivo, time.paginaTam);
	}
	return str;
}

void constroiTime() {
	Time *time = (Time*) malloc(800 * sizeof(Time));

	char arrayCaminhos[800][200];
	char controle[18950];
	int contador = 0;
	strcpy(controle, "");

	while (!(strcmp(controle, "FIM") == 0) && !(strcmp(controle, "FIM ") == 0)){
		scanf("%s", controle);
		strcpy(arrayCaminhos[contador], controle);
		contador++;
	}
	//ignora o FIM que foi armazenado na ultima posicao lida
	contador--;

	//abre todos os arquivos para processamento
	for (int i = 0; i < contador; i++) {
		time[i] = setarInformacoes(arrayCaminhos[i]);
	}
	//imprimindo na tela
	for (int i = 0; i < contador; i++) {
		imprime(time[i]);
	}
	free(time);
}

int main() {
	constroiTime();
	return 0;
}
