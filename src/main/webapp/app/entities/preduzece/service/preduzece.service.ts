import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPreduzece, NewPreduzece } from '../preduzece.model';

export type PartialUpdatePreduzece = Partial<IPreduzece> & Pick<IPreduzece, 'id'>;

export type EntityResponseType = HttpResponse<IPreduzece>;
export type EntityArrayResponseType = HttpResponse<IPreduzece[]>;

@Injectable({ providedIn: 'root' })
export class PreduzeceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/preduzeces');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(preduzece: NewPreduzece): Observable<EntityResponseType> {
    return this.http.post<IPreduzece>(this.resourceUrl, preduzece, { observe: 'response' });
  }

  update(preduzece: IPreduzece): Observable<EntityResponseType> {
    return this.http.put<IPreduzece>(`${this.resourceUrl}/${this.getPreduzeceIdentifier(preduzece)}`, preduzece, { observe: 'response' });
  }

  partialUpdate(preduzece: PartialUpdatePreduzece): Observable<EntityResponseType> {
    return this.http.patch<IPreduzece>(`${this.resourceUrl}/${this.getPreduzeceIdentifier(preduzece)}`, preduzece, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPreduzece>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPreduzece[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPreduzeceIdentifier(preduzece: Pick<IPreduzece, 'id'>): number {
    return preduzece.id;
  }

  comparePreduzece(o1: Pick<IPreduzece, 'id'> | null, o2: Pick<IPreduzece, 'id'> | null): boolean {
    return o1 && o2 ? this.getPreduzeceIdentifier(o1) === this.getPreduzeceIdentifier(o2) : o1 === o2;
  }

  addPreduzeceToCollectionIfMissing<Type extends Pick<IPreduzece, 'id'>>(
    preduzeceCollection: Type[],
    ...preduzecesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const preduzeces: Type[] = preduzecesToCheck.filter(isPresent);
    if (preduzeces.length > 0) {
      const preduzeceCollectionIdentifiers = preduzeceCollection.map(preduzeceItem => this.getPreduzeceIdentifier(preduzeceItem)!);
      const preduzecesToAdd = preduzeces.filter(preduzeceItem => {
        const preduzeceIdentifier = this.getPreduzeceIdentifier(preduzeceItem);
        if (preduzeceCollectionIdentifiers.includes(preduzeceIdentifier)) {
          return false;
        }
        preduzeceCollectionIdentifiers.push(preduzeceIdentifier);
        return true;
      });
      return [...preduzecesToAdd, ...preduzeceCollection];
    }
    return preduzeceCollection;
  }
}
