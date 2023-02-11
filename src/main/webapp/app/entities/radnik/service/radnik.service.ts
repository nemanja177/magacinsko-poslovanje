import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRadnik, NewRadnik } from '../radnik.model';

export type PartialUpdateRadnik = Partial<IRadnik> & Pick<IRadnik, 'id'>;

export type EntityResponseType = HttpResponse<IRadnik>;
export type EntityArrayResponseType = HttpResponse<IRadnik[]>;

@Injectable({ providedIn: 'root' })
export class RadnikService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/radniks');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(radnik: NewRadnik): Observable<EntityResponseType> {
    return this.http.post<IRadnik>(this.resourceUrl, radnik, { observe: 'response' });
  }

  update(radnik: IRadnik): Observable<EntityResponseType> {
    return this.http.put<IRadnik>(`${this.resourceUrl}/${this.getRadnikIdentifier(radnik)}`, radnik, { observe: 'response' });
  }

  partialUpdate(radnik: PartialUpdateRadnik): Observable<EntityResponseType> {
    return this.http.patch<IRadnik>(`${this.resourceUrl}/${this.getRadnikIdentifier(radnik)}`, radnik, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRadnik>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRadnik[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRadnikIdentifier(radnik: Pick<IRadnik, 'id'>): number {
    return radnik.id;
  }

  compareRadnik(o1: Pick<IRadnik, 'id'> | null, o2: Pick<IRadnik, 'id'> | null): boolean {
    return o1 && o2 ? this.getRadnikIdentifier(o1) === this.getRadnikIdentifier(o2) : o1 === o2;
  }

  addRadnikToCollectionIfMissing<Type extends Pick<IRadnik, 'id'>>(
    radnikCollection: Type[],
    ...radniksToCheck: (Type | null | undefined)[]
  ): Type[] {
    const radniks: Type[] = radniksToCheck.filter(isPresent);
    if (radniks.length > 0) {
      const radnikCollectionIdentifiers = radnikCollection.map(radnikItem => this.getRadnikIdentifier(radnikItem)!);
      const radniksToAdd = radniks.filter(radnikItem => {
        const radnikIdentifier = this.getRadnikIdentifier(radnikItem);
        if (radnikCollectionIdentifiers.includes(radnikIdentifier)) {
          return false;
        }
        radnikCollectionIdentifiers.push(radnikIdentifier);
        return true;
      });
      return [...radniksToAdd, ...radnikCollection];
    }
    return radnikCollection;
  }
}
