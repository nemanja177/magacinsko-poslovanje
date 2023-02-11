import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStavkaPrometnogDokumenta, NewStavkaPrometnogDokumenta } from '../stavka-prometnog-dokumenta.model';

export type PartialUpdateStavkaPrometnogDokumenta = Partial<IStavkaPrometnogDokumenta> & Pick<IStavkaPrometnogDokumenta, 'id'>;

export type EntityResponseType = HttpResponse<IStavkaPrometnogDokumenta>;
export type EntityArrayResponseType = HttpResponse<IStavkaPrometnogDokumenta[]>;

@Injectable({ providedIn: 'root' })
export class StavkaPrometnogDokumentaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/stavka-prometnog-dokumentas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(stavkaPrometnogDokumenta: NewStavkaPrometnogDokumenta): Observable<EntityResponseType> {
    return this.http.post<IStavkaPrometnogDokumenta>(this.resourceUrl, stavkaPrometnogDokumenta, { observe: 'response' });
  }

  update(stavkaPrometnogDokumenta: IStavkaPrometnogDokumenta): Observable<EntityResponseType> {
    return this.http.put<IStavkaPrometnogDokumenta>(
      `${this.resourceUrl}/${this.getStavkaPrometnogDokumentaIdentifier(stavkaPrometnogDokumenta)}`,
      stavkaPrometnogDokumenta,
      { observe: 'response' }
    );
  }

  partialUpdate(stavkaPrometnogDokumenta: PartialUpdateStavkaPrometnogDokumenta): Observable<EntityResponseType> {
    return this.http.patch<IStavkaPrometnogDokumenta>(
      `${this.resourceUrl}/${this.getStavkaPrometnogDokumentaIdentifier(stavkaPrometnogDokumenta)}`,
      stavkaPrometnogDokumenta,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStavkaPrometnogDokumenta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStavkaPrometnogDokumenta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStavkaPrometnogDokumentaIdentifier(stavkaPrometnogDokumenta: Pick<IStavkaPrometnogDokumenta, 'id'>): number {
    return stavkaPrometnogDokumenta.id;
  }

  compareStavkaPrometnogDokumenta(
    o1: Pick<IStavkaPrometnogDokumenta, 'id'> | null,
    o2: Pick<IStavkaPrometnogDokumenta, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getStavkaPrometnogDokumentaIdentifier(o1) === this.getStavkaPrometnogDokumentaIdentifier(o2) : o1 === o2;
  }

  addStavkaPrometnogDokumentaToCollectionIfMissing<Type extends Pick<IStavkaPrometnogDokumenta, 'id'>>(
    stavkaPrometnogDokumentaCollection: Type[],
    ...stavkaPrometnogDokumentasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const stavkaPrometnogDokumentas: Type[] = stavkaPrometnogDokumentasToCheck.filter(isPresent);
    if (stavkaPrometnogDokumentas.length > 0) {
      const stavkaPrometnogDokumentaCollectionIdentifiers = stavkaPrometnogDokumentaCollection.map(
        stavkaPrometnogDokumentaItem => this.getStavkaPrometnogDokumentaIdentifier(stavkaPrometnogDokumentaItem)!
      );
      const stavkaPrometnogDokumentasToAdd = stavkaPrometnogDokumentas.filter(stavkaPrometnogDokumentaItem => {
        const stavkaPrometnogDokumentaIdentifier = this.getStavkaPrometnogDokumentaIdentifier(stavkaPrometnogDokumentaItem);
        if (stavkaPrometnogDokumentaCollectionIdentifiers.includes(stavkaPrometnogDokumentaIdentifier)) {
          return false;
        }
        stavkaPrometnogDokumentaCollectionIdentifiers.push(stavkaPrometnogDokumentaIdentifier);
        return true;
      });
      return [...stavkaPrometnogDokumentasToAdd, ...stavkaPrometnogDokumentaCollection];
    }
    return stavkaPrometnogDokumentaCollection;
  }
}
