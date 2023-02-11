import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJedinicaMere, NewJedinicaMere } from '../jedinica-mere.model';

export type PartialUpdateJedinicaMere = Partial<IJedinicaMere> & Pick<IJedinicaMere, 'id'>;

export type EntityResponseType = HttpResponse<IJedinicaMere>;
export type EntityArrayResponseType = HttpResponse<IJedinicaMere[]>;

@Injectable({ providedIn: 'root' })
export class JedinicaMereService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/jedinica-meres');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(jedinicaMere: NewJedinicaMere): Observable<EntityResponseType> {
    return this.http.post<IJedinicaMere>(this.resourceUrl, jedinicaMere, { observe: 'response' });
  }

  update(jedinicaMere: IJedinicaMere): Observable<EntityResponseType> {
    return this.http.put<IJedinicaMere>(`${this.resourceUrl}/${this.getJedinicaMereIdentifier(jedinicaMere)}`, jedinicaMere, {
      observe: 'response',
    });
  }

  partialUpdate(jedinicaMere: PartialUpdateJedinicaMere): Observable<EntityResponseType> {
    return this.http.patch<IJedinicaMere>(`${this.resourceUrl}/${this.getJedinicaMereIdentifier(jedinicaMere)}`, jedinicaMere, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IJedinicaMere>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJedinicaMere[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getJedinicaMereIdentifier(jedinicaMere: Pick<IJedinicaMere, 'id'>): number {
    return jedinicaMere.id;
  }

  compareJedinicaMere(o1: Pick<IJedinicaMere, 'id'> | null, o2: Pick<IJedinicaMere, 'id'> | null): boolean {
    return o1 && o2 ? this.getJedinicaMereIdentifier(o1) === this.getJedinicaMereIdentifier(o2) : o1 === o2;
  }

  addJedinicaMereToCollectionIfMissing<Type extends Pick<IJedinicaMere, 'id'>>(
    jedinicaMereCollection: Type[],
    ...jedinicaMeresToCheck: (Type | null | undefined)[]
  ): Type[] {
    const jedinicaMeres: Type[] = jedinicaMeresToCheck.filter(isPresent);
    if (jedinicaMeres.length > 0) {
      const jedinicaMereCollectionIdentifiers = jedinicaMereCollection.map(
        jedinicaMereItem => this.getJedinicaMereIdentifier(jedinicaMereItem)!
      );
      const jedinicaMeresToAdd = jedinicaMeres.filter(jedinicaMereItem => {
        const jedinicaMereIdentifier = this.getJedinicaMereIdentifier(jedinicaMereItem);
        if (jedinicaMereCollectionIdentifiers.includes(jedinicaMereIdentifier)) {
          return false;
        }
        jedinicaMereCollectionIdentifiers.push(jedinicaMereIdentifier);
        return true;
      });
      return [...jedinicaMeresToAdd, ...jedinicaMereCollection];
    }
    return jedinicaMereCollection;
  }
}
