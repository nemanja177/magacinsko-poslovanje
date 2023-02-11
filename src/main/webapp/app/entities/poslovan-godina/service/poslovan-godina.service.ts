import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPoslovanGodina, NewPoslovanGodina } from '../poslovan-godina.model';

export type PartialUpdatePoslovanGodina = Partial<IPoslovanGodina> & Pick<IPoslovanGodina, 'id'>;

export type EntityResponseType = HttpResponse<IPoslovanGodina>;
export type EntityArrayResponseType = HttpResponse<IPoslovanGodina[]>;

@Injectable({ providedIn: 'root' })
export class PoslovanGodinaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/poslovan-godinas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(poslovanGodina: NewPoslovanGodina): Observable<EntityResponseType> {
    return this.http.post<IPoslovanGodina>(this.resourceUrl, poslovanGodina, { observe: 'response' });
  }

  update(poslovanGodina: IPoslovanGodina): Observable<EntityResponseType> {
    return this.http.put<IPoslovanGodina>(`${this.resourceUrl}/${this.getPoslovanGodinaIdentifier(poslovanGodina)}`, poslovanGodina, {
      observe: 'response',
    });
  }

  partialUpdate(poslovanGodina: PartialUpdatePoslovanGodina): Observable<EntityResponseType> {
    return this.http.patch<IPoslovanGodina>(`${this.resourceUrl}/${this.getPoslovanGodinaIdentifier(poslovanGodina)}`, poslovanGodina, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPoslovanGodina>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPoslovanGodina[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPoslovanGodinaIdentifier(poslovanGodina: Pick<IPoslovanGodina, 'id'>): number {
    return poslovanGodina.id;
  }

  comparePoslovanGodina(o1: Pick<IPoslovanGodina, 'id'> | null, o2: Pick<IPoslovanGodina, 'id'> | null): boolean {
    return o1 && o2 ? this.getPoslovanGodinaIdentifier(o1) === this.getPoslovanGodinaIdentifier(o2) : o1 === o2;
  }

  addPoslovanGodinaToCollectionIfMissing<Type extends Pick<IPoslovanGodina, 'id'>>(
    poslovanGodinaCollection: Type[],
    ...poslovanGodinasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const poslovanGodinas: Type[] = poslovanGodinasToCheck.filter(isPresent);
    if (poslovanGodinas.length > 0) {
      const poslovanGodinaCollectionIdentifiers = poslovanGodinaCollection.map(
        poslovanGodinaItem => this.getPoslovanGodinaIdentifier(poslovanGodinaItem)!
      );
      const poslovanGodinasToAdd = poslovanGodinas.filter(poslovanGodinaItem => {
        const poslovanGodinaIdentifier = this.getPoslovanGodinaIdentifier(poslovanGodinaItem);
        if (poslovanGodinaCollectionIdentifiers.includes(poslovanGodinaIdentifier)) {
          return false;
        }
        poslovanGodinaCollectionIdentifiers.push(poslovanGodinaIdentifier);
        return true;
      });
      return [...poslovanGodinasToAdd, ...poslovanGodinaCollection];
    }
    return poslovanGodinaCollection;
  }
}
