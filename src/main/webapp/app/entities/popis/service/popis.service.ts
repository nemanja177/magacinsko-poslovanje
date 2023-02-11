import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPopis, NewPopis } from '../popis.model';

export type PartialUpdatePopis = Partial<IPopis> & Pick<IPopis, 'id'>;

type RestOf<T extends IPopis | NewPopis> = Omit<T, 'datumPopisa'> & {
  datumPopisa?: string | null;
};

export type RestPopis = RestOf<IPopis>;

export type NewRestPopis = RestOf<NewPopis>;

export type PartialUpdateRestPopis = RestOf<PartialUpdatePopis>;

export type EntityResponseType = HttpResponse<IPopis>;
export type EntityArrayResponseType = HttpResponse<IPopis[]>;

@Injectable({ providedIn: 'root' })
export class PopisService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/popis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(popis: NewPopis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(popis);
    return this.http.post<RestPopis>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(popis: IPopis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(popis);
    return this.http
      .put<RestPopis>(`${this.resourceUrl}/${this.getPopisIdentifier(popis)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(popis: PartialUpdatePopis): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(popis);
    return this.http
      .patch<RestPopis>(`${this.resourceUrl}/${this.getPopisIdentifier(popis)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPopis>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPopis[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPopisIdentifier(popis: Pick<IPopis, 'id'>): number {
    return popis.id;
  }

  comparePopis(o1: Pick<IPopis, 'id'> | null, o2: Pick<IPopis, 'id'> | null): boolean {
    return o1 && o2 ? this.getPopisIdentifier(o1) === this.getPopisIdentifier(o2) : o1 === o2;
  }

  addPopisToCollectionIfMissing<Type extends Pick<IPopis, 'id'>>(
    popisCollection: Type[],
    ...popisToCheck: (Type | null | undefined)[]
  ): Type[] {
    const popis: Type[] = popisToCheck.filter(isPresent);
    if (popis.length > 0) {
      const popisCollectionIdentifiers = popisCollection.map(popisItem => this.getPopisIdentifier(popisItem)!);
      const popisToAdd = popis.filter(popisItem => {
        const popisIdentifier = this.getPopisIdentifier(popisItem);
        if (popisCollectionIdentifiers.includes(popisIdentifier)) {
          return false;
        }
        popisCollectionIdentifiers.push(popisIdentifier);
        return true;
      });
      return [...popisToAdd, ...popisCollection];
    }
    return popisCollection;
  }

  protected convertDateFromClient<T extends IPopis | NewPopis | PartialUpdatePopis>(popis: T): RestOf<T> {
    return {
      ...popis,
      datumPopisa: popis.datumPopisa?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restPopis: RestPopis): IPopis {
    return {
      ...restPopis,
      datumPopisa: restPopis.datumPopisa ? dayjs(restPopis.datumPopisa) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPopis>): HttpResponse<IPopis> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPopis[]>): HttpResponse<IPopis[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
