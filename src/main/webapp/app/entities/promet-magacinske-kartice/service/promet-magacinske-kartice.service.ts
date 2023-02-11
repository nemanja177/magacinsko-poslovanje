import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrometMagacinskeKartice, NewPrometMagacinskeKartice } from '../promet-magacinske-kartice.model';

export type PartialUpdatePrometMagacinskeKartice = Partial<IPrometMagacinskeKartice> & Pick<IPrometMagacinskeKartice, 'id'>;

type RestOf<T extends IPrometMagacinskeKartice | NewPrometMagacinskeKartice> = Omit<T, 'datumPrometa'> & {
  datumPrometa?: string | null;
};

export type RestPrometMagacinskeKartice = RestOf<IPrometMagacinskeKartice>;

export type NewRestPrometMagacinskeKartice = RestOf<NewPrometMagacinskeKartice>;

export type PartialUpdateRestPrometMagacinskeKartice = RestOf<PartialUpdatePrometMagacinskeKartice>;

export type EntityResponseType = HttpResponse<IPrometMagacinskeKartice>;
export type EntityArrayResponseType = HttpResponse<IPrometMagacinskeKartice[]>;

@Injectable({ providedIn: 'root' })
export class PrometMagacinskeKarticeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/promet-magacinske-kartices');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(prometMagacinskeKartice: NewPrometMagacinskeKartice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prometMagacinskeKartice);
    return this.http
      .post<RestPrometMagacinskeKartice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(prometMagacinskeKartice: IPrometMagacinskeKartice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prometMagacinskeKartice);
    return this.http
      .put<RestPrometMagacinskeKartice>(`${this.resourceUrl}/${this.getPrometMagacinskeKarticeIdentifier(prometMagacinskeKartice)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(prometMagacinskeKartice: PartialUpdatePrometMagacinskeKartice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prometMagacinskeKartice);
    return this.http
      .patch<RestPrometMagacinskeKartice>(
        `${this.resourceUrl}/${this.getPrometMagacinskeKarticeIdentifier(prometMagacinskeKartice)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPrometMagacinskeKartice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPrometMagacinskeKartice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrometMagacinskeKarticeIdentifier(prometMagacinskeKartice: Pick<IPrometMagacinskeKartice, 'id'>): number {
    return prometMagacinskeKartice.id;
  }

  comparePrometMagacinskeKartice(
    o1: Pick<IPrometMagacinskeKartice, 'id'> | null,
    o2: Pick<IPrometMagacinskeKartice, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getPrometMagacinskeKarticeIdentifier(o1) === this.getPrometMagacinskeKarticeIdentifier(o2) : o1 === o2;
  }

  addPrometMagacinskeKarticeToCollectionIfMissing<Type extends Pick<IPrometMagacinskeKartice, 'id'>>(
    prometMagacinskeKarticeCollection: Type[],
    ...prometMagacinskeKarticesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const prometMagacinskeKartices: Type[] = prometMagacinskeKarticesToCheck.filter(isPresent);
    if (prometMagacinskeKartices.length > 0) {
      const prometMagacinskeKarticeCollectionIdentifiers = prometMagacinskeKarticeCollection.map(
        prometMagacinskeKarticeItem => this.getPrometMagacinskeKarticeIdentifier(prometMagacinskeKarticeItem)!
      );
      const prometMagacinskeKarticesToAdd = prometMagacinskeKartices.filter(prometMagacinskeKarticeItem => {
        const prometMagacinskeKarticeIdentifier = this.getPrometMagacinskeKarticeIdentifier(prometMagacinskeKarticeItem);
        if (prometMagacinskeKarticeCollectionIdentifiers.includes(prometMagacinskeKarticeIdentifier)) {
          return false;
        }
        prometMagacinskeKarticeCollectionIdentifiers.push(prometMagacinskeKarticeIdentifier);
        return true;
      });
      return [...prometMagacinskeKarticesToAdd, ...prometMagacinskeKarticeCollection];
    }
    return prometMagacinskeKarticeCollection;
  }

  protected convertDateFromClient<T extends IPrometMagacinskeKartice | NewPrometMagacinskeKartice | PartialUpdatePrometMagacinskeKartice>(
    prometMagacinskeKartice: T
  ): RestOf<T> {
    return {
      ...prometMagacinskeKartice,
      datumPrometa: prometMagacinskeKartice.datumPrometa?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restPrometMagacinskeKartice: RestPrometMagacinskeKartice): IPrometMagacinskeKartice {
    return {
      ...restPrometMagacinskeKartice,
      datumPrometa: restPrometMagacinskeKartice.datumPrometa ? dayjs(restPrometMagacinskeKartice.datumPrometa) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPrometMagacinskeKartice>): HttpResponse<IPrometMagacinskeKartice> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPrometMagacinskeKartice[]>): HttpResponse<IPrometMagacinskeKartice[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
