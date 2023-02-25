import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DokumentService {
  constructor() {}

  getDocumentById(id: number) {
    var head;
    var token = JSON.parse(localStorage.getItem('token'));
    if (token) {
      head = {
        Authorization: 'Bearer ' + token,
        'Content-Type': 'application/json',
      };
    } else {
      head = {
        'Content-Type': 'application/json',
      };
    }
    let httpOptions = {
      header: new HttpHeaders(head),
    };

    return this.http.get(this.url + '/' + id, { headers: httpOptions.header });
  }
}
