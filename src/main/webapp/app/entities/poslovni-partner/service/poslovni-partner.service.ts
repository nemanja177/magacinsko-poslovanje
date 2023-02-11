import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPoslovniPartner, NewPoslovniPartner } from '../poslovni-partner.model';

export type PartialUpdatePoslovniPartner = Partial<IPoslovniPartner> & Pick<IPoslovniPartner, 'id'>;

export type EntityResponseType = HttpResponse<IPoslovniPartner>;
export type EntityArrayResponseType = HttpResponse<IPoslovniPartner[]>;

@Injectable({ providedIn: 'root' })
export class PoslovniPartnerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/poslovni-partners');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(poslovniPartner: NewPoslovniPartner): Observable<EntityResponseType> {
    return this.http.post<IPoslovniPartner>(this.resourceUrl, poslovniPartner, { observe: 'response' });
  }

  update(poslovniPartner: IPoslovniPartner): Observable<EntityResponseType> {
    return this.http.put<IPoslovniPartner>(`${this.resourceUrl}/${this.getPoslovniPartnerIdentifier(poslovniPartner)}`, poslovniPartner, {
      observe: 'response',
    });
  }

  partialUpdate(poslovniPartner: PartialUpdatePoslovniPartner): Observable<EntityResponseType> {
    return this.http.patch<IPoslovniPartner>(`${this.resourceUrl}/${this.getPoslovniPartnerIdentifier(poslovniPartner)}`, poslovniPartner, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPoslovniPartner>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPoslovniPartner[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPoslovniPartnerIdentifier(poslovniPartner: Pick<IPoslovniPartner, 'id'>): number {
    return poslovniPartner.id;
  }

  comparePoslovniPartner(o1: Pick<IPoslovniPartner, 'id'> | null, o2: Pick<IPoslovniPartner, 'id'> | null): boolean {
    return o1 && o2 ? this.getPoslovniPartnerIdentifier(o1) === this.getPoslovniPartnerIdentifier(o2) : o1 === o2;
  }

  addPoslovniPartnerToCollectionIfMissing<Type extends Pick<IPoslovniPartner, 'id'>>(
    poslovniPartnerCollection: Type[],
    ...poslovniPartnersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const poslovniPartners: Type[] = poslovniPartnersToCheck.filter(isPresent);
    if (poslovniPartners.length > 0) {
      const poslovniPartnerCollectionIdentifiers = poslovniPartnerCollection.map(
        poslovniPartnerItem => this.getPoslovniPartnerIdentifier(poslovniPartnerItem)!
      );
      const poslovniPartnersToAdd = poslovniPartners.filter(poslovniPartnerItem => {
        const poslovniPartnerIdentifier = this.getPoslovniPartnerIdentifier(poslovniPartnerItem);
        if (poslovniPartnerCollectionIdentifiers.includes(poslovniPartnerIdentifier)) {
          return false;
        }
        poslovniPartnerCollectionIdentifiers.push(poslovniPartnerIdentifier);
        return true;
      });
      return [...poslovniPartnersToAdd, ...poslovniPartnerCollection];
    }
    return poslovniPartnerCollection;
  }
}
