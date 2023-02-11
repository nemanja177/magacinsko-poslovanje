import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMagacinskaKartica } from '../magacinska-kartica.model';
import { MagacinskaKarticaService } from '../service/magacinska-kartica.service';

@Injectable({ providedIn: 'root' })
export class MagacinskaKarticaRoutingResolveService implements Resolve<IMagacinskaKartica | null> {
  constructor(protected service: MagacinskaKarticaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMagacinskaKartica | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((magacinskaKartica: HttpResponse<IMagacinskaKartica>) => {
          if (magacinskaKartica.body) {
            return of(magacinskaKartica.body);
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
