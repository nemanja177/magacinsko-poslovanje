import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMagacin, NewMagacin } from '../magacin.model';

export type PartialUpdateMagacin = Partial<IMagacin> & Pick<IMagacin, 'id'>;

export type EntityResponseType = HttpResponse<IMagacin>;
export type EntityArrayResponseType = HttpResponse<IMagacin[]>;

@Injectable({ providedIn: 'root' })
export class MagacinService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/magacins');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(magacin: NewMagacin): Observable<EntityResponseType> {
    return this.http.post<IMagacin>(this.resourceUrl, magacin, { observe: 'response' });
  }

  update(magacin: IMagacin): Observable<EntityResponseType> {
    return this.http.put<IMagacin>(`${this.resourceUrl}/${this.getMagacinIdentifier(magacin)}`, magacin, { observe: 'response' });
  }

  partialUpdate(magacin: PartialUpdateMagacin): Observable<EntityResponseType> {
    return this.http.patch<IMagacin>(`${this.resourceUrl}/${this.getMagacinIdentifier(magacin)}`, magacin, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMagacin>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMagacin[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMagacinIdentifier(magacin: Pick<IMagacin, 'id'>): number {
    return magacin.id;
  }

  compareMagacin(o1: Pick<IMagacin, 'id'> | null, o2: Pick<IMagacin, 'id'> | null): boolean {
    return o1 && o2 ? this.getMagacinIdentifier(o1) === this.getMagacinIdentifier(o2) : o1 === o2;
  }

  addMagacinToCollectionIfMissing<Type extends Pick<IMagacin, 'id'>>(
    magacinCollection: Type[],
    ...magacinsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const magacins: Type[] = magacinsToCheck.filter(isPresent);
    if (magacins.length > 0) {
      const magacinCollectionIdentifiers = magacinCollection.map(magacinItem => this.getMagacinIdentifier(magacinItem)!);
      const magacinsToAdd = magacins.filter(magacinItem => {
        const magacinIdentifier = this.getMagacinIdentifier(magacinItem);
        if (magacinCollectionIdentifiers.includes(magacinIdentifier)) {
          return false;
        }
        magacinCollectionIdentifiers.push(magacinIdentifier);
        return true;
      });
      return [...magacinsToAdd, ...magacinCollection];
    }
    return magacinCollection;
  }
}
