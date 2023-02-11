import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IArtikal, NewArtikal } from '../artikal.model';

export type PartialUpdateArtikal = Partial<IArtikal> & Pick<IArtikal, 'id'>;

export type EntityResponseType = HttpResponse<IArtikal>;
export type EntityArrayResponseType = HttpResponse<IArtikal[]>;

@Injectable({ providedIn: 'root' })
export class ArtikalService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/artikals');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(artikal: NewArtikal): Observable<EntityResponseType> {
    return this.http.post<IArtikal>(this.resourceUrl, artikal, { observe: 'response' });
  }

  update(artikal: IArtikal): Observable<EntityResponseType> {
    return this.http.put<IArtikal>(`${this.resourceUrl}/${this.getArtikalIdentifier(artikal)}`, artikal, { observe: 'response' });
  }

  partialUpdate(artikal: PartialUpdateArtikal): Observable<EntityResponseType> {
    return this.http.patch<IArtikal>(`${this.resourceUrl}/${this.getArtikalIdentifier(artikal)}`, artikal, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IArtikal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IArtikal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getArtikalIdentifier(artikal: Pick<IArtikal, 'id'>): number {
    return artikal.id;
  }

  compareArtikal(o1: Pick<IArtikal, 'id'> | null, o2: Pick<IArtikal, 'id'> | null): boolean {
    return o1 && o2 ? this.getArtikalIdentifier(o1) === this.getArtikalIdentifier(o2) : o1 === o2;
  }

  addArtikalToCollectionIfMissing<Type extends Pick<IArtikal, 'id'>>(
    artikalCollection: Type[],
    ...artikalsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const artikals: Type[] = artikalsToCheck.filter(isPresent);
    if (artikals.length > 0) {
      const artikalCollectionIdentifiers = artikalCollection.map(artikalItem => this.getArtikalIdentifier(artikalItem)!);
      const artikalsToAdd = artikals.filter(artikalItem => {
        const artikalIdentifier = this.getArtikalIdentifier(artikalItem);
        if (artikalCollectionIdentifiers.includes(artikalIdentifier)) {
          return false;
        }
        artikalCollectionIdentifiers.push(artikalIdentifier);
        return true;
      });
      return [...artikalsToAdd, ...artikalCollection];
    }
    return artikalCollection;
  }
}
