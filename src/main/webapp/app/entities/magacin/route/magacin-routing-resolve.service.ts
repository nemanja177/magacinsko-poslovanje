import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMagacin } from '../magacin.model';
import { MagacinService } from '../service/magacin.service';

@Injectable({ providedIn: 'root' })
export class MagacinRoutingResolveService implements Resolve<IMagacin | null> {
  constructor(protected service: MagacinService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMagacin | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((magacin: HttpResponse<IMagacin>) => {
          if (magacin.body) {
            return of(magacin.body);
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
