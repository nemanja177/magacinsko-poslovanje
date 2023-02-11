import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPoslovniPartner } from '../poslovni-partner.model';
import { PoslovniPartnerService } from '../service/poslovni-partner.service';

@Injectable({ providedIn: 'root' })
export class PoslovniPartnerRoutingResolveService implements Resolve<IPoslovniPartner | null> {
  constructor(protected service: PoslovniPartnerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPoslovniPartner | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((poslovniPartner: HttpResponse<IPoslovniPartner>) => {
          if (poslovniPartner.body) {
            return of(poslovniPartner.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
