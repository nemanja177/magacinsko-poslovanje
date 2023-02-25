import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Radnik } from '../models/Radnik';

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
export class RadnikService {
  private apiRadnikUrl = `${environment.apiURL}/api/radnici`;

  constructor(private http: HttpClient) {}

  getRadnik(): Observable<Radnik[]> {
    return this.http.get<Radnik[]>(this.apiRadnikUrl);
  }

  getRadnikById(id: number): Observable<any> {
    return this.http.get(`${this.apiRadnikUrl}/${id}`);
  }

  updateRadnik(id: number, radnici: Radnik): Observable<Radnik> {
    return this.http.put<Radnik>(`${this.apiRadnikUrl}/${id}`, JSON.stringify(radnici), uploadHeader);
  }

  createRadnik(radnici: Radnik): Observable<Radnik> {
    return this.http.post<Radnik>(this.apiRadnikUrl, radnici, createHeader);
  }

  blockRadnik(id: any): Observable<any> {
    console.log('bravo + ', id);
    return this.http.delete<Radnik>(`${this.apiRadnikUrl}/${id}`);
  }
}
