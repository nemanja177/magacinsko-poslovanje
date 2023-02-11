import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnalitickaMagacinskaKartica, NewAnalitickaMagacinskaKartica } from '../analiticka-magacinska-kartica.model';

export type PartialUpdateAnalitickaMagacinskaKartica = Partial<IAnalitickaMagacinskaKartica> & Pick<IAnalitickaMagacinskaKartica, 'id'>;

type RestOf<T extends IAnalitickaMagacinskaKartica | NewAnalitickaMagacinskaKartica> = Omit<T, 'datumPrometa'> & {
  datumPrometa?: string | null;
};

export type RestAnalitickaMagacinskaKartica = RestOf<IAnalitickaMagacinskaKartica>;

export type NewRestAnalitickaMagacinskaKartica = RestOf<NewAnalitickaMagacinskaKartica>;

export type PartialUpdateRestAnalitickaMagacinskaKartica = RestOf<PartialUpdateAnalitickaMagacinskaKartica>;

export type EntityResponseType = HttpResponse<IAnalitickaMagacinskaKartica>;
export type EntityArrayResponseType = HttpResponse<IAnalitickaMagacinskaKartica[]>;

@Injectable({ providedIn: 'root' })
export class AnalitickaMagacinskaKarticaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/analiticka-magacinska-karticas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(analitickaMagacinskaKartica: NewAnalitickaMagacinskaKartica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(analitickaMagacinskaKartica);
    return this.http
      .post<RestAnalitickaMagacinskaKartica>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(analitickaMagacinskaKartica: IAnalitickaMagacinskaKartica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(analitickaMagacinskaKartica);
    return this.http
      .put<RestAnalitickaMagacinskaKartica>(
        `${this.resourceUrl}/${this.getAnalitickaMagacinskaKarticaIdentifier(analitickaMagacinskaKartica)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(analitickaMagacinskaKartica: PartialUpdateAnalitickaMagacinskaKartica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(analitickaMagacinskaKartica);
    return this.http
      .patch<RestAnalitickaMagacinskaKartica>(
        `${this.resourceUrl}/${this.getAnalitickaMagacinskaKarticaIdentifier(analitickaMagacinskaKartica)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAnalitickaMagacinskaKartica>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAnalitickaMagacinskaKartica[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnalitickaMagacinskaKarticaIdentifier(analitickaMagacinskaKartica: Pick<IAnalitickaMagacinskaKartica, 'id'>): number {
    return analitickaMagacinskaKartica.id;
  }

  compareAnalitickaMagacinskaKartica(
    o1: Pick<IAnalitickaMagacinskaKartica, 'id'> | null,
    o2: Pick<IAnalitickaMagacinskaKartica, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getAnalitickaMagacinskaKarticaIdentifier(o1) === this.getAnalitickaMagacinskaKarticaIdentifier(o2) : o1 === o2;
  }

  addAnalitickaMagacinskaKarticaToCollectionIfMissing<Type extends Pick<IAnalitickaMagacinskaKartica, 'id'>>(
    analitickaMagacinskaKarticaCollection: Type[],
    ...analitickaMagacinskaKarticasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const analitickaMagacinskaKarticas: Type[] = analitickaMagacinskaKarticasToCheck.filter(isPresent);
    if (analitickaMagacinskaKarticas.length > 0) {
      const analitickaMagacinskaKarticaCollectionIdentifiers = analitickaMagacinskaKarticaCollection.map(
        analitickaMagacinskaKarticaItem => this.getAnalitickaMagacinskaKarticaIdentifier(analitickaMagacinskaKarticaItem)!
      );
      const analitickaMagacinskaKarticasToAdd = analitickaMagacinskaKarticas.filter(analitickaMagacinskaKarticaItem => {
        const analitickaMagacinskaKarticaIdentifier = this.getAnalitickaMagacinskaKarticaIdentifier(analitickaMagacinskaKarticaItem);
        if (analitickaMagacinskaKarticaCollectionIdentifiers.includes(analitickaMagacinskaKarticaIdentifier)) {
          return false;
        }
        analitickaMagacinskaKarticaCollectionIdentifiers.push(analitickaMagacinskaKarticaIdentifier);
        return true;
      });
      return [...analitickaMagacinskaKarticasToAdd, ...analitickaMagacinskaKarticaCollection];
    }
    return analitickaMagacinskaKarticaCollection;
  }

  protected convertDateFromClient<
    T extends IAnalitickaMagacinskaKartica | NewAnalitickaMagacinskaKartica | PartialUpdateAnalitickaMagacinskaKartica
  >(analitickaMagacinskaKartica: T): RestOf<T> {
    return {
      ...analitickaMagacinskaKartica,
      datumPrometa: analitickaMagacinskaKartica.datumPrometa?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAnalitickaMagacinskaKartica: RestAnalitickaMagacinskaKartica): IAnalitickaMagacinskaKartica {
    return {
      ...restAnalitickaMagacinskaKartica,
      datumPrometa: restAnalitickaMagacinskaKartica.datumPrometa ? dayjs(restAnalitickaMagacinskaKartica.datumPrometa) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAnalitickaMagacinskaKartica>): HttpResponse<IAnalitickaMagacinskaKartica> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestAnalitickaMagacinskaKartica[]>
  ): HttpResponse<IAnalitickaMagacinskaKartica[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
