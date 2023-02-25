import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Dokument } from '../models/dokument';
import { Observable } from 'rxjs';

const createHeader = {
  headers: new HttpHeaders({
    method: 'POST',
    'Content-Type': 'application/json',
  }),
};

const uploadHeader = {
  headers: new HttpHeaders({
    method: 'PUT',
    'Content-Type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root',
})
export class DokumentService {
  private apiDokumentsUrl = `${environment.apiURL}/api/dokumenti`;

  constructor(private http: HttpClient) {}

  getDokument(): Observable<Dokument[]> {
    return this.http.get<Dokument[]>(this.apiDokumentsUrl);
  }

  getDokumentById(id: number): Observable<any> {
    return this.http.get(`${this.apiDokumentsUrl}/${id}`);
  }

  updateDokument(id: number, dokuments: Dokument): Observable<Dokument> {
    return this.http.put<Dokument>(`${this.apiDokumentsUrl}/${id}`, JSON.stringify(dokuments), uploadHeader);
  }

  createDokument(dokuments: Dokument): Observable<Dokument> {
    return this.http.post<Dokument>(this.apiDokumentsUrl, dokuments, createHeader);
  }

  blockDokument(id: any): Observable<any> {
    console.log('bravo + ', id);
    return this.http.delete<Dokument>(`${this.apiDokumentsUrl}/${id}`);
  }
}
