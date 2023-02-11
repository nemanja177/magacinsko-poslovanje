import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMagacinskaKartica, NewMagacinskaKartica } from '../magacinska-kartica.model';

export type PartialUpdateMagacinskaKartica = Partial<IMagacinskaKartica> & Pick<IMagacinskaKartica, 'id'>;

export type EntityResponseType = HttpResponse<IMagacinskaKartica>;
export type EntityArrayResponseType = HttpResponse<IMagacinskaKartica[]>;

@Injectable({ providedIn: 'root' })
export class MagacinskaKarticaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/magacinska-karticas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(magacinskaKartica: NewMagacinskaKartica): Observable<EntityResponseType> {
    return this.http.post<IMagacinskaKartica>(this.resourceUrl, magacinskaKartica, { observe: 'response' });
  }

  update(magacinskaKartica: IMagacinskaKartica): Observable<EntityResponseType> {
    return this.http.put<IMagacinskaKartica>(
      `${this.resourceUrl}/${this.getMagacinskaKarticaIdentifier(magacinskaKartica)}`,
      magacinskaKartica,
      { observe: 'response' }
    );
  }

  partialUpdate(magacinskaKartica: PartialUpdateMagacinskaKartica): Observable<EntityResponseType> {
    return this.http.patch<IMagacinskaKartica>(
      `${this.resourceUrl}/${this.getMagacinskaKarticaIdentifier(magacinskaKartica)}`,
      magacinskaKartica,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMagacinskaKartica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMagacinskaKartica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMagacinskaKarticaIdentifier(magacinskaKartica: Pick<IMagacinskaKartica, 'id'>): number {
    return magacinskaKartica.id;
  }

  compareMagacinskaKartica(o1: Pick<IMagacinskaKartica, 'id'> | null, o2: Pick<IMagacinskaKartica, 'id'> | null): boolean {
    return o1 && o2 ? this.getMagacinskaKarticaIdentifier(o1) === this.getMagacinskaKarticaIdentifier(o2) : o1 === o2;
  }

  addMagacinskaKarticaToCollectionIfMissing<Type extends Pick<IMagacinskaKartica, 'id'>>(
    magacinskaKarticaCollection: Type[],
    ...magacinskaKarticasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const magacinskaKarticas: Type[] = magacinskaKarticasToCheck.filter(isPresent);
    if (magacinskaKarticas.length > 0) {
      const magacinskaKarticaCollectionIdentifiers = magacinskaKarticaCollection.map(
        magacinskaKarticaItem => this.getMagacinskaKarticaIdentifier(magacinskaKarticaItem)!
      );
      const magacinskaKarticasToAdd = magacinskaKarticas.filter(magacinskaKarticaItem => {
        const magacinskaKarticaIdentifier = this.getMagacinskaKarticaIdentifier(magacinskaKarticaItem);
        if (magacinskaKarticaCollectionIdentifiers.includes(magacinskaKarticaIdentifier)) {
          return false;
        }
        magacinskaKarticaCollectionIdentifiers.push(magacinskaKarticaIdentifier);
        return true;
      });
      return [...magacinskaKarticasToAdd, ...magacinskaKarticaCollection];
    }
    return magacinskaKarticaCollection;
  }
}
