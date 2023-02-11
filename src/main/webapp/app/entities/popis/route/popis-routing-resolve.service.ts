import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPopis } from '../popis.model';
import { PopisService } from '../service/popis.service';

@Injectable({ providedIn: 'root' })
export class PopisRoutingResolveService implements Resolve<IPopis | null> {
  constructor(protected service: PopisService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPopis | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((popis: HttpResponse<IPopis>) => {
          if (popis.body) {
            return of(popis.body);
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
