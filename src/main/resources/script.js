"use strict"

function requestAJAX(inputId){
let xhr = new XMLHttpRequest(); //Создаём переменную для запроса на сервер
let URL = "http://localhost:9090/?id=" + inputId;
  
switch(inputId){
	case "printBase":
		xhr.onreadystatechange = function() {
			if ((xhr.readyState == 4) && (xhr.status == 200)) { //Проверка условия загрузки страницы, а также ответа
				out2.innerHTML = "<td class='idTd'>Id</td><td class='titleTd'>Title</td><td class='yearTd'>Year of publishing</td><td class='authorIdTd'>Id of author</td>"
				let books = JSON.parse(xhr.responseText);
				for(let i = 0; i < books.length; i++){ // Вывод элементов на страницу
				out2.innerHTML += "<tr><td class='idTd'>" + books[i].id + "</td><td class='titleTd'>" + books[i].title +
				"</td><td class='yearTd'>" + books[i].year + "</td><td class='authorIdTd'>" + books[i].idauthor + "</td></tr>";
				}
			}
		}
	break;

	case "printBaser":
		xhr.onreadystatechange = function() {
			if ((xhr.readyState == 4) && (xhr.status == 200)) { //Проверка условия загрузки страницы, а также ответа
				out3.innerHTML = "<td class='idTd'>Id</td><td class='nameAuthorTd'>Name of Author</td>"
				let authors = JSON.parse(xhr.responseText);
				for(let i = 0; i < authors.length; i++){ // Вывод элементов на страницу
				out3.innerHTML += "<tr><td class='idTd'>" + authors[i].Id + "</td><td class='nameAuthorTd'>" + authors[i].NameAuthor + "</td></tr>";
				}
			}
		}
	break;

	case "addBook":
		let bookName = document.getElementById('bookName').value;
		let year = document.getElementById('year').value;
		let nameAuthor = document.getElementById('nameAuthor').value;
		if(nameAuthor == "") // проверка на отсутствие символов
		nameAuthor = "(without author)"
		if(bookName == "")
		bookName = "(without book)"
		URL += "&bookName=" + bookName + "&year=" + year + "&nameAuthor=" + nameAuthor;
	
	break;

	case "changeTitleBook":
		let idBook = document.getElementById('idBook').value;
		let bookName1 = document.getElementById('bookName1').value;
			if(bookName1 == "") // проверка на отсутствие символов
				bookName1 = "(without book)"
		URL += "&idBook=" + idBook + "&bookName=" + bookName1;
	
	break;

	case "deleteBook":
		let idBook1 = document.getElementById('idBook1').value;
		URL += "&idBook=" + idBook1;
	
	break;

	case "printBookAuthor":
		let idAuthor = document.getElementById('idAuthor').value;
		URL += "&idAuthor=" + idAuthor;
		xhr.onreadystatechange = function() {
			if ((xhr.readyState==4) && (xhr.status==200)) { //Проверка условия загрузки страницы, а также ответа
				out.innerHTML = "<td class='idTd'>Id</td><td class='titleTd'>Name Book</td><td class='yearTd'>Year of publishing</td><td class='authorIdTd'>Id of author</td>"
				let booksAuthors = JSON.parse(xhr.responseText);
				for(let i = 0; i < booksAuthors.length; i++){ // Вывод элементов на страницу
				out.innerHTML += "<tr><td class='idTd'>" + booksAuthors[i].Id + "</td><td class='titleTd'>" + booksAuthors[i].NameBook 
				+ "</td><td class='yearTd'>" + booksAuthors[i].Year + "</td><td class='authorIdTd'>" 
				+ booksAuthors[i].AuthorID + "</td></tr>";
				}
			}
		} 
	break;

	case "Popular":
		xhr.onreadystatechange = function() {
			if ((xhr.readyState==4) && (xhr.status==200)) { //Проверка условия загрузки страницы, а также ответа
				out1.innerHTML = "<td class='nameAuthorTd'>Name of author</td><td class='countTd'>Count</td>"
				let popular = JSON.parse(xhr.responseText);
				for(let i = 0; i < popular.length; i++){// Вывод элементов на страницу
				out1.innerHTML += "<tr><td class='nameAuthorTd'>" + popular[i].NameAuthor 
				+ "</td><td class='countTd'>" + popular[i].Count + "</td></tr>";
				}
			}
		} 
	break;
}
  
xhr.open('GET', URL, true);
xhr.send(); //Отправка запроса
}