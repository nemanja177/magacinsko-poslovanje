import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStavkaPopisa, NewStavkaPopisa } from '../stavka-popisa.model';

export type PartialUpdateStavkaPopisa = Partial<IStavkaPopisa> & Pick<IStavkaPopisa, 'id'>;

export type EntityResponseType = HttpResponse<IStavkaPopisa>;
export type EntityArrayResponseType = HttpResponse<IStavkaPopisa[]>;

@Injectable({ providedIn: 'root' })
export class StavkaPopisaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/stavka-popisas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(stavkaPopisa: NewStavkaPopisa): Observable<EntityResponseType> {
    return this.http.post<IStavkaPopisa>(this.resourceUrl, stavkaPopisa, { observe: 'response' });
  }

  update(stavkaPopisa: IStavkaPopisa): Observable<EntityResponseType> {
    return this.http.put<IStavkaPopisa>(`${this.resourceUrl}/${this.getStavkaPopisaIdentifier(stavkaPopisa)}`, stavkaPopisa, {
      observe: 'response',
    });
  }

  partialUpdate(stavkaPopisa: PartialUpdateStavkaPopisa): Observable<EntityResponseType> {
    return this.http.patch<IStavkaPopisa>(`${this.resourceUrl}/${this.getStavkaPopisaIdentifier(stavkaPopisa)}`, stavkaPopisa, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStavkaPopisa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStavkaPopisa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStavkaPopisaIdentifier(stavkaPopisa: Pick<IStavkaPopisa, 'id'>): number {
    return stavkaPopisa.id;
  }

  compareStavkaPopisa(o1: Pick<IStavkaPopisa, 'id'> | null, o2: Pick<IStavkaPopisa, 'id'> | null): boolean {
    return o1 && o2 ? this.getStavkaPopisaIdentifier(o1) === this.getStavkaPopisaIdentifier(o2) : o1 === o2;
  }

  addStavkaPopisaToCollectionIfMissing<Type extends Pick<IStavkaPopisa, 'id'>>(
    stavkaPopisaCollection: Type[],
    ...stavkaPopisasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const stavkaPopisas: Type[] = stavkaPopisasToCheck.filter(isPresent);
    if (stavkaPopisas.length > 0) {
      const stavkaPopisaCollectionIdentifiers = stavkaPopisaCollection.map(
        stavkaPopisaItem => this.getStavkaPopisaIdentifier(stavkaPopisaItem)!
      );
      const stavkaPopisasToAdd = stavkaPopisas.filter(stavkaPopisaItem => {
        const stavkaPopisaIdentifier = this.getStavkaPopisaIdentifier(stavkaPopisaItem);
        if (stavkaPopisaCollectionIdentifiers.includes(stavkaPopisaIdentifier)) {
          return false;
        }
        stavkaPopisaCollectionIdentifiers.push(stavkaPopisaIdentifier);
        return true;
      });
      return [...stavkaPopisasToAdd, ...stavkaPopisaCollection];
    }
    return stavkaPopisaCollection;
  }
}
