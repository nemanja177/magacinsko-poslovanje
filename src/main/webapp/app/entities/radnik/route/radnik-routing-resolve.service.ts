import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRadnik } from '../radnik.model';
import { RadnikService } from '../service/radnik.service';

@Injectable({ providedIn: 'root' })
export class RadnikRoutingResolveService implements Resolve<IRadnik | null> {
  constructor(protected service: RadnikService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRadnik | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((radnik: HttpResponse<IRadnik>) => {
          if (radnik.body) {
            return of(radnik.body);
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
