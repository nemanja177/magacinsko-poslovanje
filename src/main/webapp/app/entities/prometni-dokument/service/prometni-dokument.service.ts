import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrometniDokument, NewPrometniDokument } from '../prometni-dokument.model';

export type PartialUpdatePrometniDokument = Partial<IPrometniDokument> & Pick<IPrometniDokument, 'id'>;

type RestOf<T extends IPrometniDokument | NewPrometniDokument> = Omit<T, 'datum'> & {
  datum?: string | null;
};

export type RestPrometniDokument = RestOf<IPrometniDokument>;

export type NewRestPrometniDokument = RestOf<NewPrometniDokument>;

export type PartialUpdateRestPrometniDokument = RestOf<PartialUpdatePrometniDokument>;

export type EntityResponseType = HttpResponse<IPrometniDokument>;
export type EntityArrayResponseType = HttpResponse<IPrometniDokument[]>;

@Injectable({ providedIn: 'root' })
export class PrometniDokumentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/prometni-dokuments');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(prometniDokument: NewPrometniDokument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prometniDokument);
    return this.http
      .post<RestPrometniDokument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(prometniDokument: IPrometniDokument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prometniDokument);
    return this.http
      .put<RestPrometniDokument>(`${this.resourceUrl}/${this.getPrometniDokumentIdentifier(prometniDokument)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(prometniDokument: PartialUpdatePrometniDokument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prometniDokument);
    return this.http
      .patch<RestPrometniDokument>(`${this.resourceUrl}/${this.getPrometniDokumentIdentifier(prometniDokument)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPrometniDokument>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPrometniDokument[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrometniDokumentIdentifier(prometniDokument: Pick<IPrometniDokument, 'id'>): number {
    return prometniDokument.id;
  }

  comparePrometniDokument(o1: Pick<IPrometniDokument, 'id'> | null, o2: Pick<IPrometniDokument, 'id'> | null): boolean {
    return o1 && o2 ? this.getPrometniDokumentIdentifier(o1) === this.getPrometniDokumentIdentifier(o2) : o1 === o2;
  }

  addPrometniDokumentToCollectionIfMissing<Type extends Pick<IPrometniDokument, 'id'>>(
    prometniDokumentCollection: Type[],
    ...prometniDokumentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const prometniDokuments: Type[] = prometniDokumentsToCheck.filter(isPresent);
    if (prometniDokuments.length > 0) {
      const prometniDokumentCollectionIdentifiers = prometniDokumentCollection.map(
        prometniDokumentItem => this.getPrometniDokumentIdentifier(prometniDokumentItem)!
      );
      const prometniDokumentsToAdd = prometniDokuments.filter(prometniDokumentItem => {
        const prometniDokumentIdentifier = this.getPrometniDokumentIdentifier(prometniDokumentItem);
        if (prometniDokumentCollectionIdentifiers.includes(prometniDokumentIdentifier)) {
          return false;
        }
        prometniDokumentCollectionIdentifiers.push(prometniDokumentIdentifier);
        return true;
      });
      return [...prometniDokumentsToAdd, ...prometniDokumentCollection];
    }
    return prometniDokumentCollection;
  }

  protected convertDateFromClient<T extends IPrometniDokument | NewPrometniDokument | PartialUpdatePrometniDokument>(
    prometniDokument: T
  ): RestOf<T> {
    return {
      ...prometniDokument,
      datum: prometniDokument.datum?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restPrometniDokument: RestPrometniDokument): IPrometniDokument {
    return {
      ...restPrometniDokument,
      datum: restPrometniDokument.datum ? dayjs(restPrometniDokument.datum) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPrometniDokument>): HttpResponse<IPrometniDokument> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPrometniDokument[]>): HttpResponse<IPrometniDokument[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
